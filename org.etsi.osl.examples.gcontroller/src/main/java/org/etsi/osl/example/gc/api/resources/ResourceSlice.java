package org.etsi.osl.example.gc.api.resources;

import com.itav.slicemanager.models.OperationStatusResponse;
import com.itav.slicemanager.models.Slice;
import lombok.Data;
import org.etsi.osl.domain.model.DomainModelDefinition;
import org.etsi.osl.domain.model.ITMFRCM634_ModelTransformer;
import org.etsi.osl.domain.model.ITMFRI639_ModelTransformer;
import org.etsi.osl.example.gc.api.config.CatalogClient;
import org.etsi.osl.example.gc.api.defaults.DefaultsFactory;
import org.etsi.osl.example.gc.api.mapper.ObjectToOslCharacteristicsMapper;
import org.etsi.osl.example.gc.api.mapper.resources.SliceMapper;
import org.etsi.osl.example.gc.api.services.ApiResult;
import org.etsi.osl.example.gc.api.services.SliceManagerService;
import org.etsi.osl.tmf.common.model.Any;
import org.etsi.osl.tmf.common.model.ELifecycle;
import org.etsi.osl.tmf.common.model.EValueType;
import org.etsi.osl.tmf.common.model.service.ServiceStateType;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecification;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCharacteristic;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCharacteristicValue;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCreate;
import org.etsi.osl.tmf.ri639.model.*;
import org.etsi.osl.tmf.sim638.model.ServiceUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@Data
public class ResourceSlice extends DomainModelDefinition
    implements ITMFRCM634_ModelTransformer, ITMFRI639_ModelTransformer {

    // TODO validation error


    private static final Logger logger = LoggerFactory.getLogger("org.etsi.osl.example.gc");

    public static final String OSL_RSPEC_TYPE = "LogicalResourceSpecification";
    public static final String RESOURCE_NAME = "ResourceSlice";
    public static final String RESOURCE_DESCRIPTION = "Resource Slice description";

    private static CatalogClient aCatalogClient;
    private static SliceManagerService sliceManagerService;
    private static SliceMapper sliceMapper;

    public ResourceSlice(@Value("${osl-controller.category}") String version,
                         @Value("${osl-controller.version}") String category
    ) {
        super("", RESOURCE_NAME, version, RESOURCE_DESCRIPTION, category);
    }

    @Autowired
    public void setStaticDependencies(
        CatalogClient catalogClient,
        SliceManagerService sliceManagerService,
        SliceMapper sliceMapper
    ) {
        ResourceSlice.aCatalogClient = catalogClient;
        ResourceSlice.sliceManagerService = sliceManagerService;
        ResourceSlice.sliceMapper = sliceMapper;
    }


    @Override
    public ResourceSpecificationCreate toRSpecCreate_InitRepo() {

        ResourceSpecificationCreate rsc = new ResourceSpecificationCreate();

        rsc.setName(this.name);
        rsc.setVersion(this.version);
        rsc.setDescription(this.description);
        rsc.setType(OSL_RSPEC_TYPE);
        rsc.setCategory(this.category);

        rsc.setLifecycleStatus(ELifecycle.ACTIVE.getValue());

        ResourceSpecificationCharacteristic ci = ObjectToOslCharacteristicsMapper.addResourceSpecCharacteristicItemShort(
            "Kind", this.category, null, EValueType.TEXT, this.category, "", true
        );
        rsc.addResourceSpecificationCharacteristicItem(ci);

        rsc.addResourceSpecificationCharacteristicItem(new ResourceSpecificationCharacteristic()
            .name("isCreated")
            .valueType(EValueType.BOOLEAN.getValue())
            .addResourceSpecCharacteristicValueItem(new ResourceSpecificationCharacteristicValue()
                .value(new Any("false"))
                .valueType(EValueType.BOOLEAN.getValue())
                .isDefault(true)
            )
        );

        // TODO: isUpdating not needed in RFS
        rsc.addResourceSpecificationCharacteristicItem(new ResourceSpecificationCharacteristic()
            .name("isUpdating")
            .valueType(EValueType.BOOLEAN.getValue())
            .addResourceSpecCharacteristicValueItem(new ResourceSpecificationCharacteristicValue()
                .value(new Any("false"))
                .valueType(EValueType.BOOLEAN.getValue())
                .isDefault(true)
            )
        );

        Slice defaultSlice = DefaultsFactory.getSlice();
        for (ResourceSpecificationCharacteristic rchar : sliceMapper.toResourceSpecCharacteristics(defaultSlice)) {
            rsc.addResourceSpecificationCharacteristicItem(rchar);
        }

        return rsc;
    }


    @Override
    public DomainModelDefinition fromRSpec(ResourceSpecification rSpec) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public ResourceCreate toResourceCreate() throws Exception {
        return null;
    }

    /**
     * Kicks off slice creation upstream and hands back a {@code resourceUpdate} carrying the
     * asynchronous operation's {@code status.creation.requestId}, which {@code PollingService} polls
     * to completion. On any failure to even submit the request, the resource is marked SUSPENDED
     * directly and carries no request id, so the caller does not enqueue it for polling.
     */
    public static ResourceUpdate createResourceLogic(ResourceUpdate resourceUpdate, String resourceid) {
        Slice slice;
        try {
            slice = sliceMapper.fromResourceCharacteristics(resourceUpdate.getResourceCharacteristic());
        } catch (IllegalArgumentException e) {
            logger.error("Error mapping resource characteristics to Slice for resource {}: {}", resourceid, e.getMessage());
            resourceUpdate.setResourceStatus(ResourceStatusType.SUSPENDED);
            return resourceUpdate;
        }

        // slice_id in the API is the same as the OSL resource id
        slice.setSliceId(resourceid);
        resourceUpdate.getResourceCharacteristic().removeIf(c -> "slice_id".equals(c.getName()));
        resourceUpdate.addResourceCharacteristicItem(new Characteristic()
            .name("slice_id")
            .valueType(EValueType.TEXT.getValue())
            .value(new Any(resourceid)));

        Instant startTime = Instant.now();
        UUID requestId = switch (sliceManagerService.createSlice(slice)) {
            case ApiResult.Success<OperationStatusResponse> s -> s.value().getRequestId();
            default -> null;
        };
        if (requestId == null) {
            logger.error("Slice Manager returned no request id for creation of slice {}", resourceid);
            resourceUpdate.setResourceStatus(ResourceStatusType.SUSPENDED);
            return resourceUpdate;
        }

        LifecycleCharacteristics.setCreationRequestId(resourceUpdate, requestId);
        LifecycleCharacteristics.setCreationStart(resourceUpdate, requestId, startTime);
        resourceUpdate.setResourceStatus(ResourceStatusType.RESERVED);
        logger.info("Create of slice {} accepted upstream as operation {}; polling for completion", resourceid, requestId);
        return resourceUpdate;
    }

    /**
     * Kicks off slice deletion upstream, symmetric with {@link #createResourceLogic}.
     */
    public static ResourceUpdate deleteResourceLogic(ResourceUpdate resourceUpdate, String resourceid) {
        UUID requestId = switch (sliceManagerService.deleteSlice(resourceid)) {
            case ApiResult.Success<OperationStatusResponse> s -> s.value().getRequestId();
            default -> null;
        };
        if (requestId == null) {
            logger.error("Slice Manager returned no request id for deletion of slice {}", resourceid);
            resourceUpdate.setResourceStatus(ResourceStatusType.SUSPENDED);
            return resourceUpdate;
        }

        LifecycleCharacteristics.setDeletionRequestId(resourceUpdate, requestId);
        logger.info("Delete of slice {} accepted upstream as operation {}; polling for completion", resourceid, requestId);
        return resourceUpdate;
    }

    public static void triggerDeletionUEs(Resource sliceResource) {
        // change status of all related UE services to TERMINATED

        for (ResourceRelationship relationship : sliceResource.getResourceRelationship()) {
            // UE Relationships
            org.etsi.osl.tmf.sim638.model.Service service = aCatalogClient.retrieveServiceRelatedToResourceId(relationship.getResource().getId());
            if (service == null) {
                logger.warn("No related service found for resource relationship with id {}", relationship.getResource().getId());
                continue;
            }
            logger.info("Triggering deletion of related UE service with id {} due to slice deletion", service.getId());
            service.setState(ServiceStateType.TERMINATED);
            ServiceUpdate serviceUpdate = new ServiceUpdate().state(ServiceStateType.TERMINATED);
            service = aCatalogClient.updateService(service.getId(), serviceUpdate, true);
            logger.info("Updated related service with id {} to state {}", service.getId(), serviceUpdate.getState());
        }

    }

}