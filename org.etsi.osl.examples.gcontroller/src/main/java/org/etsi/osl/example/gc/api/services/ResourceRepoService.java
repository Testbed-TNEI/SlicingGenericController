package org.etsi.osl.example.gc.api.services;

import org.etsi.osl.example.gc.api.config.CatalogClient;
import org.etsi.osl.example.gc.api.mapper.SimpleResourceMapper;
import org.etsi.osl.example.gc.api.resources.LifecycleCharacteristics;
import org.etsi.osl.example.gc.api.resources.ResourceA;
import org.etsi.osl.example.gc.api.resources.ResourceSlice;
import org.etsi.osl.example.gc.api.resources.ResourceUE;
import org.etsi.osl.example.gc.api.services.PollingService.Operation;
import org.etsi.osl.tmf.ri639.model.Resource;
import org.etsi.osl.tmf.ri639.model.ResourceCreate;
import org.etsi.osl.tmf.ri639.model.ResourceStatusType;
import org.etsi.osl.tmf.ri639.model.ResourceUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Handles the CREATE/UPDATE/DELETE requests OSOM sends over JMS.
 *
 * <p>These methods must return quickly. OSOM blocks on the reply with a 20 second timeout and
 * treats a timeout as a deployment failure, so none of them may wait on the Slice Manager's
 * asynchronous operation to actually finish. For CREATE/DELETE of ResourceSlice/ResourceUE, this
 * class submits the request itself (fast: the Slice Manager only has to accept it and hand back a
 * {@code request_id}) and stores that id as the {@code status.requestId} characteristic. The
 * request is then handed to {@link PollingService}, which polls it to completion and settles the
 * resource's final status. OSL's own 30 second poll loop waits for that to happen.
 */
@Service
public class ResourceRepoService {

    private static final Logger logger = LoggerFactory.getLogger("org.etsi.osl.example.gc");

    private static final Set<String> SUPPORTED_SPECS =
        Set.of(ResourceA.RESOURCE_NAME, ResourceSlice.RESOURCE_NAME, ResourceUE.RESOURCE_NAME);

    @Autowired
    CatalogClient aCatalogClient;

    @Autowired
    SimpleResourceMapper simpleResourceMapper;

    @Autowired
    PollingService pollingService;

    public Resource createResource(Map<String, Object> headers, ResourceCreate resourceRequested) {

        Instant messageArrivedAt = Instant.now();
        String resourceid = resourceId(headers);
        String specName = resourceRequested.getResourceSpecification().getName();

        if (!SUPPORTED_SPECS.contains(specName)) {
            logger.error("Unsupported resource specification {} for resource {}", specName, resourceid);
            return null;
        }

        ResourceUpdate resourceUpdate = simpleResourceMapper.resourceCreateToResourceUpdate(resourceRequested);
        resourceUpdate.setResourceStatus(ResourceStatusType.RESERVED);
        PollingService.setIsCreated(resourceUpdate, false);

        // ResourceSlice/ResourceUE submit the request upstream here (fast: 202 Accepted + request_id)
        // and carry status.requestId for PollingService to poll. ResourceA has nothing to submit; its
        // (also fast) logic still runs off the queue, same as before.
        resourceUpdate = switch (specName) {
            case ResourceSlice.RESOURCE_NAME -> ResourceSlice.createResourceLogic(resourceUpdate, resourceid);
            case ResourceUE.RESOURCE_NAME -> ResourceUE.createResourceLogic(resourceUpdate, resourceid);
            default -> resourceUpdate;
        };

        if (resourceUpdate.getResourceStatus().equals(ResourceStatusType.SUSPENDED)) {
            return this.updateResource(resourceid, resourceUpdate);
        }

        UUID creationRequestId = LifecycleCharacteristics.getCreationRequestId(resourceUpdate);
        if (creationRequestId != null) {
            LifecycleCharacteristics.setCreationMessageTimestamp(resourceUpdate, creationRequestId, messageArrivedAt);
        }

        this.enqueue(Operation.CREATE, specName, resourceid, resourceUpdate);
        return this.updateResource(resourceid, resourceUpdate);
    }

    public Resource updateResource(Map<String, Object> headers, ResourceUpdate r) {

        String resourceid = resourceId(headers);
        String specName = r.getResourceSpecification().getName();

        if (!Set.of(ResourceA.RESOURCE_NAME).contains(specName)) {
            logger.error("Unsupported resource specification {} for update of resource {}", specName, resourceid);
            return null;
        }

        this.enqueue(Operation.UPDATE, specName, resourceid, r);
        return this.updateResource(resourceid, r);
    }

    public Resource deleteResource(Map<String, Object> headers, ResourceUpdate r) {

        String resourceid = resourceId(headers);
        String specName = r.getResourceSpecification().getName();

        if (!Set.of(ResourceUE.RESOURCE_NAME, ResourceSlice.RESOURCE_NAME).contains(specName)) {
            logger.error("Unsupported resource specification {} for delete of resource {}", specName, resourceid);
            return null;
        }

        if (!PollingService.wasCreated(r)) {
            // Nothing was ever provisioned upstream, so there is nothing to submit a delete for.
            logger.info("Resource {} was never created upstream; marking SUSPENDED without a delete call", resourceid);
            r.setResourceStatus(ResourceStatusType.SUSPENDED);
            PollingService.setIsCreated(r, false);
            return this.updateResource(resourceid, r);
        }

        logger.info("Submitting deletion of resource with id: {}", resourceid);
        ResourceUpdate resourceUpdate = switch (specName) {
            case ResourceSlice.RESOURCE_NAME -> ResourceSlice.deleteResourceLogic(r, resourceid);
            case ResourceUE.RESOURCE_NAME -> ResourceUE.deleteResourceLogic(r, resourceid);
            default -> r;
        };

        if (resourceUpdate.getResourceStatus().equals(ResourceStatusType.SUSPENDED)) {
            return this.updateResource(resourceid, resourceUpdate);
        }

        this.enqueue(Operation.DELETE, specName, resourceid, resourceUpdate);
        return this.updateResource(resourceid, resourceUpdate);
    }

    private Resource updateResource(String resourceId, ResourceUpdate resourceUpdate) {
        Resource updated = aCatalogClient.updateResourceById(resourceId, resourceUpdate);
        if (updated == null) {
            logger.error("Could not update {} of resource {} in the catalog", Operation.UPDATE, resourceId);
        }
        return updated;
    }

    /**
     * Queues the polling work for a request already written to the catalog.
     */
    private void enqueue(Operation operation, String specName, String resourceId, ResourceUpdate resourceUpdate) {

        try {
            pollingService.enqueue(operation, specName, resourceId, resourceUpdate);
        } catch (Exception e) {
            logger.error("Could not queue {} of resource {}", operation, resourceId, e);
            resourceUpdate.setResourceStatus(ResourceStatusType.SUSPENDED);
        }
    }

    private String resourceId(Map<String, Object> headers) {
        return (String) headers.get(PollingService.HEADER_RESOURCE_ID);
    }

}