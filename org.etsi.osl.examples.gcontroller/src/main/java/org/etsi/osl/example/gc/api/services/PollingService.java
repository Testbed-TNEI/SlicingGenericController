package org.etsi.osl.example.gc.api.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itav.slicemanager.models.OperationStatusResponse;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsConstants;
import org.etsi.osl.example.gc.api.config.CatalogClient;
import org.etsi.osl.example.gc.api.resources.LifecycleCharacteristics;
import org.etsi.osl.example.gc.api.resources.ResourceA;
import org.etsi.osl.example.gc.api.resources.ResourceSlice;
import org.etsi.osl.example.gc.api.resources.ResourceUE;
import org.etsi.osl.tmf.common.model.Any;
import org.etsi.osl.tmf.common.model.EValueType;
import org.etsi.osl.tmf.ri639.model.Characteristic;
import org.etsi.osl.tmf.ri639.model.Resource;
import org.etsi.osl.tmf.ri639.model.ResourceStatusType;
import org.etsi.osl.tmf.ri639.model.ResourceUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Runs the Slice Manager calls off the OSOM-facing routes.
 *
 * <p>OSOM's CREATE/UPDATE/DELETE request-reply has a 20 second timeout (Camel's default) and it
 * treats a missing reply as failure: {@code GCOrchestrationService} sets the service TERMINATED,
 * which queues a spurious DELETE for a resource still being provisioned. The Slice Manager API is
 * asynchronous (every core write returns {@code 202 Accepted} plus a {@code request_id}), so
 * {@link ResourceRepoService} submits the request itself (fast) and stores the {@code request_id} via
 * {@link LifecycleCharacteristics}; this service's job is to poll {@code GET /operations/{request_id}}
 * until it reaches a terminal state, record that outcome, and settle the resource on its final
 * status.
 *
 * <p>{@code ResourceSlice}/{@code ResourceUE} store the request id via
 * {@link LifecycleCharacteristics} right after submitting it; this service only reads it back to know
 * what to poll.
 *
 * <p>OSL already supports slow provisioning: {@code GenericControllerDeploymentReq.bpmn} polls
 * {@code GCOrchestrationCheckDeploymentService} every 30 seconds until the service reaches ACTIVE
 * or TERMINATED. So the reply only has to acknowledge the request; the resource status writeback
 * carries the outcome.
 *
 * <p>The pending work lives in the broker rather than in this JVM: each request is republished as
 * a persistent message on the provisioning queue and only acknowledged once the writeback lands, so
 * a restart mid-polling replays it instead of losing it. Messages are grouped by
 * {@code JMSXGroupID = resourceId}, which makes Artemis deliver all work for one resource to a
 * single consumer in order — that is what keeps a DELETE from overtaking the CREATE it cancels,
 * without any lock or shared state here.
 */
@Service
public class PollingService {

    private static final Logger logger = LoggerFactory.getLogger("org.etsi.osl.example.gc");

    public static final String HEADER_RESOURCE_ID = "org.etsi.osl.resourceId";
    public static final String HEADER_OPERATION = "org.etsi.osl.gc.operation";
    public static final String HEADER_SPEC_NAME = "org.etsi.osl.gc.specName";

    private static final Duration POLL_TIMEOUT = Duration.ofMinutes(1);
    private static final Duration POLL_INTERVAL = Duration.ofMillis(100);

    public enum Operation {CREATE, UPDATE, DELETE}

    @Autowired
    private ProducerTemplate template;

    @Autowired
    private CatalogClient aCatalogClient;

    @Autowired
    private SliceManagerService sliceManagerService;

    @Value("${osl-controller.category}")
    private String compcategory;

    @Value("${osl-controller.version}")
    private String compversion;

    /**
     * @return the queue the OSOM-facing routes publish to and the polling route consumes
     */
    public String provisioningQueue() {
        return "jms:queue:SLICING_PROVISION/" + compcategory + "/" + compversion;
    }

    /**
     * Hands a request to the polling queue. Persistent by default, so it outlives a restart.
     */
    public void enqueue(Operation operation, String specName, String resourceId, ResourceUpdate resourceUpdate)
        throws IOException {

        Map<String, Object> headers = new HashMap<>();
        headers.put(HEADER_RESOURCE_ID, resourceId);
        headers.put(HEADER_OPERATION, operation.name());
        headers.put(HEADER_SPEC_NAME, specName);
        // Per-resource FIFO: Artemis pins a group to one consumer, so operations on the same
        // resource can never overlap or reorder, while different resources still run in parallel.
        headers.put(JmsConstants.JMS_X_GROUP_ID, resourceId);

        template.sendBodyAndHeaders(provisioningQueue(), toJsonString(resourceUpdate), headers);
        logger.info("Enqueued {} for resource {} ({})", operation, resourceId, specName);
    }

    /**
     * Entry point of the polling route. Any exception escaping here rolls the JMS transaction
     * back, so the message is redelivered.
     */
    public void handle(Map<String, Object> headers, ResourceUpdate resourceUpdate) {
        String resourceId = (String) headers.get(HEADER_RESOURCE_ID);
        String specName = (String) headers.get(HEADER_SPEC_NAME);
        Operation operation = Operation.valueOf((String) headers.get(HEADER_OPERATION));

        long startTime = System.nanoTime();
        logger.info("Polling {} for resource {} ({})", operation, resourceId, specName);

        switch (operation) {
            case CREATE -> handleCreate(specName, resourceId, resourceUpdate);
            case UPDATE -> handleUpdate(specName, resourceId, resourceUpdate);
            case DELETE -> handleDelete(specName, resourceId, resourceUpdate);
        }

        logger.info("Polling {} for resource {} took {} ms",
            operation, resourceId, (System.nanoTime() - startTime) / 1_000_000);
    }

    private void handleCreate(String specName, String resourceId, ResourceUpdate resourceUpdate) {
        switch (specName) {
            // ResourceA has no upstream call to poll: its logic already ran synchronously.
            case ResourceA.RESOURCE_NAME ->
                finalizeInstant(resourceId, ResourceA.resourceLogic(resourceUpdate, resourceId));
            case ResourceSlice.RESOURCE_NAME, ResourceUE.RESOURCE_NAME ->
                pollAndFinalizeCreate(specName, resourceId, resourceUpdate);
            default -> logger.error("No create logic known for resource {} ({})", resourceId, specName);
        }
    }

    private void finalizeInstant(String resourceId, ResourceUpdate result) {
        if (result == null) {
            logger.error("No create logic produced a result for resource {}", resourceId);
            return;
        }

        boolean created = result.getResourceStatus() == ResourceStatusType.AVAILABLE;
        setIsCreated(result, created);

        Resource res = aCatalogClient.updateResourceById(resourceId, result);
        logger.info("Create of resource {} finished with status {}", resourceId, result.getResourceStatus());
        if (res == null) {
            // The writeback is what OSL's poll loop watches; without it the service never settles.
            throw new IllegalStateException("Could not write back the result for resource " + resourceId);
        }
    }

    /**
     * ResourceSlice/ResourceUE already submitted the create request in {@link ResourceRepoService};
     * this polls {@code status.creation.requestId} to a terminal state (or {@link #POLL_TIMEOUT}) and
     * settles the resource accordingly.
     */
    private void pollAndFinalizeCreate(String specName, String resourceId, ResourceUpdate resourceUpdate) {
        UUID requestId = LifecycleCharacteristics.getCreationRequestId(resourceUpdate);
        if (requestId == null) {
            // createResourceLogic already marked this SUSPENDED because it could not even submit the
            // request upstream; nothing to poll, just write it back.
            aCatalogClient.updateResourceById(resourceId, resourceUpdate);
            return;
        }

        OperationStatusResponse outcome = pollUntilTerminal(requestId);
        LifecycleCharacteristics.setCreationEnd(resourceUpdate, requestId, Instant.now());
        LifecycleCharacteristics.setCreationOutcome(resourceUpdate, outcome);

        boolean succeeded = (outcome.getState() == OperationStatusResponse.StateEnum.COMPLETED);
        resourceUpdate.setResourceStatus(succeeded ? ResourceStatusType.AVAILABLE : ResourceStatusType.SUSPENDED);
        setIsCreated(resourceUpdate, succeeded);

        Resource res = aCatalogClient.updateResourceById(resourceId, resourceUpdate);
        logger.info("Create of resource {} settled with operation state {}; resource status {}",
            resourceId, outcome.getState(), resourceUpdate.getResourceStatus());
        if (res == null) {
            throw new IllegalStateException("Could not write back the result for resource " + resourceId);
        }

        if (succeeded && specName.equals(ResourceUE.RESOURCE_NAME)) {
            ResourceUE.createResourceRefsLogic(resourceUpdate, resourceId);
        }
    }

    private void handleUpdate(String specName, String resourceId, ResourceUpdate resourceUpdate) {
        // Migration decision: no update/patch support for ResourceSlice/ResourceUE, so only ResourceA
        // has real update logic here.
        ResourceUpdate result = switch (specName) {
            case ResourceA.RESOURCE_NAME -> ResourceA.resourceLogic(resourceUpdate, resourceId);
            default -> null;
        };

        if (result == null) {
            logger.error("No update logic produced a result for resource {} ({})", resourceId, specName);
            return;
        }

        if (aCatalogClient.updateResourceById(resourceId, result) == null) {
            throw new IllegalStateException("Could not write back the result for resource " + resourceId);
        }
    }

    private void handleDelete(String specName, String resourceId, ResourceUpdate resourceUpdate) {
        // Nothing was ever provisioned upstream, so there is nothing to tear down. Still mark the
        // resource SUSPENDED: Service#findNextStateBasedOnResourceList only moves the service to
        // TERMINATED once every supporting resource is SUSPENDED, so skipping this leaves OSL's
        // 30 second poll loop spinning forever.
        if (!wasCreated(resourceUpdate)) {
            logger.info("Resource {} was never created upstream; marking SUSPENDED without a delete call", resourceId);
            resourceUpdate.setResourceStatus(ResourceStatusType.SUSPENDED);
            setIsCreated(resourceUpdate, false);
            aCatalogClient.updateResourceById(resourceId, resourceUpdate);
            return;
        }

        UUID requestId = LifecycleCharacteristics.getDeletionRequestId(resourceUpdate);
        boolean deleted = false;
        if (requestId != null) {
            OperationStatusResponse outcome = pollUntilTerminal(requestId);
            LifecycleCharacteristics.setDeletionOutcome(resourceUpdate, outcome);
            logger.info("Delete of resource {} settled with operation state {}", resourceId, outcome.getState());
            deleted = (outcome.getState() == OperationStatusResponse.StateEnum.COMPLETED);
        }

        // Either way there is nothing left to retry from here: SUSPENDED lets the service settle on
        // TERMINATED instead of the 30 second poll loop spinning forever. isCreated only clears when
        // the delete actually completed, so it keeps reflecting whether the resource may still exist
        // upstream, same as the handleFailure contract.
        resourceUpdate.setResourceStatus(ResourceStatusType.SUSPENDED);
        setIsCreated(resourceUpdate, !deleted && wasCreated(resourceUpdate));
        Resource res = aCatalogClient.updateResourceById(resourceId, resourceUpdate);

        if (specName.equals(ResourceSlice.RESOURCE_NAME) && res != null) {
            ResourceSlice.triggerDeletionUEs(res);
        }
    }

    /**
     * Busy-polls {@code GET /operations/{request_id}} until it reaches a terminal state or
     * {@link #POLL_TIMEOUT} elapses (treated as failure). Always returns a response carrying
     * {@code requestId}, synthesizing a {@code FAILED} one if the timeout or an interruption struck
     * before a terminal (or any) status was ever fetched.
     */
    private OperationStatusResponse pollUntilTerminal(UUID requestId) {
        Instant deadline = Instant.now().plus(POLL_TIMEOUT);
        OperationStatusResponse status = fetchOperationStatus(requestId);
        while (!isTerminal(status) && Instant.now().isBefore(deadline)) {
            try {
                Thread.sleep(POLL_INTERVAL.toMillis());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Interrupted while polling operation {}", requestId);
                return status != null ? status : failed(requestId);
            }
            status = fetchOperationStatus(requestId);
        }

        if (!isTerminal(status)) {
            logger.warn("Operation {} did not reach a terminal state within {}; treating as failed", requestId, POLL_TIMEOUT);
            return failed(requestId);
        }
        return status;
    }

    private static OperationStatusResponse failed(UUID requestId) {
        return new OperationStatusResponse(requestId, OperationStatusResponse.StateEnum.FAILED);
    }

    /**
     * NotFound and Error both mean "no usable status yet" here, same as the operation still being
     * pending/processing: the caller just retries on the next poll until {@link #POLL_TIMEOUT}.
     */
    private OperationStatusResponse fetchOperationStatus(UUID requestId) {
        return sliceManagerService.getOperationStatus(requestId) instanceof ApiResult.Success<OperationStatusResponse> s
            ? s.value() : null;
    }

    private static boolean isTerminal(OperationStatusResponse status) {
        return status != null && (status.getState() == OperationStatusResponse.StateEnum.COMPLETED
            || status.getState() == OperationStatusResponse.StateEnum.FAILED);
    }

    /**
     * Last resort when a request has exhausted its redeliveries.
     *
     * <p>The right outcome depends on the operation:
     *
     * <ul>
     *   <li>CREATE and DELETE settle SUSPENDED so the service can move on to TERMINATED instead of
     *       polling forever. {@code isCreated} is left untouched: a failed CREATE never got to set it
     *       true, and a failed DELETE never got to clear it, so in both cases it still reflects
     *       whether the resource may exist upstream.
     *   <li>UPDATE is the opposite case. It never created anything, so deleting would not roll the
     *       update back — it would destroy a slice that predates the request and is still carrying
     *       traffic. A real rollback would mean re-applying the previous configuration, which is
     *       not retained. Flag it as ALARM instead, which
     *       {@code Service#findNextStateBasedOnResourceList} maps to an INACTIVE service.
     * </ul>
     *
     * @param body the in-flight {@link ResourceUpdate}, or the raw payload if the failure happened
     *             before it could be deserialized
     */
    public void handleFailure(Map<String, Object> headers, Object body, Exception cause) {
        String resourceId = (String) headers.get(HEADER_RESOURCE_ID);
        String specName = (String) headers.get(HEADER_SPEC_NAME);
        Operation operation = Operation.valueOf((String) headers.get(HEADER_OPERATION));
        ResourceUpdate resourceUpdate = body instanceof ResourceUpdate ru ? ru : new ResourceUpdate();

        logger.error("Giving up on {} for resource {} ({})", operation, resourceId, specName, cause);

        switch (operation) {
            case CREATE, DELETE -> {
                resourceUpdate.setResourceStatus(ResourceStatusType.SUSPENDED);
                aCatalogClient.updateResourceById(resourceId, resourceUpdate);
            }
            case UPDATE -> {
                logger.warn("Resource {} stays provisioned after a failed update; marking ALARM", resourceId);
                resourceUpdate.setResourceStatus(ResourceStatusType.ALARM);
                aCatalogClient.updateResourceById(resourceId, resourceUpdate);
            }
        }
    }

    public static boolean wasCreated(ResourceUpdate resourceUpdate) {
        if (resourceUpdate.getResourceCharacteristic() == null) {
            return false;
        }
        Optional<Characteristic> isCreated = resourceUpdate.getResourceCharacteristic()
            .stream()
            .filter(c -> c.getName().equals("isCreated"))
            .findFirst();

        return isCreated.isPresent()
            && isCreated.get().getValue() != null
            && Objects.equals(isCreated.get().getValue().getValue(), "true");
    }

    /**
     * Sets the {@code isCreated} characteristic, replacing any existing one.
     *
     * <p>Appending is not safe: the incoming payload already carries an {@code isCreated} from the
     * resource specification default, and a second copy makes characteristic parsing fail with
     * "Duplicate key" in mappers that collect characteristics with {@code Collectors.toMap} keyed on
     * the name.
     */
    public static void setIsCreated(ResourceUpdate resourceUpdate, boolean value) {
        if (resourceUpdate.getResourceCharacteristic() != null) {
            resourceUpdate.getResourceCharacteristic().removeIf(c -> "isCreated".equals(c.getName()));
        }
        resourceUpdate.addResourceCharacteristicItem(isCreated(value));
    }

    public static Characteristic isCreated(boolean value) {
        return new Characteristic()
            .name("isCreated")
            .valueType(EValueType.BOOLEAN.getValue())
            .value(new Any(String.valueOf(value)));
    }

    private static String toJsonString(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }

}