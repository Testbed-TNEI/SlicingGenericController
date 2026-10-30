package org.etsi.osl.example.gc.api.resources;

import com.itav.slicemanager.models.OperationStatusResponse;
import com.itav.slicemanager.models.UeSliceAssociationCreateRequest;
import lombok.Data;
import org.etsi.osl.domain.model.DomainModelDefinition;
import org.etsi.osl.domain.model.ITMFRCM634_ModelTransformer;
import org.etsi.osl.domain.model.ITMFRI639_ModelTransformer;
import org.etsi.osl.example.gc.api.config.CatalogClient;
import org.etsi.osl.example.gc.api.defaults.DefaultsFactory;
import org.etsi.osl.example.gc.api.mapper.ObjectToOslCharacteristicsMapper;
import org.etsi.osl.example.gc.api.mapper.resources.UeSliceAssociationMapper;
import org.etsi.osl.example.gc.api.services.ApiResult;
import org.etsi.osl.example.gc.api.services.SliceManagerService;
import org.etsi.osl.tmf.common.model.Any;
import org.etsi.osl.tmf.common.model.ELifecycle;
import org.etsi.osl.tmf.common.model.EValueType;
import org.etsi.osl.tmf.common.model.service.ResourceRef;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecification;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCharacteristic;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCreate;
import org.etsi.osl.tmf.ri639.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Data
public class ResourceUE extends DomainModelDefinition
    implements ITMFRCM634_ModelTransformer, ITMFRI639_ModelTransformer {


    private static final Logger logger = LoggerFactory.getLogger("org.etsi.osl.example.gc");

    public static final String OSL_RSPEC_TYPE = "LogicalResourceSpecification";
    public static final String RESOURCE_NAME = "ResourceUE";
    public static final String RESOURCE_DESCRIPTION = "Resource UE description";

    private static CatalogClient aCatalogClient;
    private static SliceManagerService sliceManagerService;
    private static UeSliceAssociationMapper ueSliceAssociationMapper;

    public ResourceUE(@Value("${osl-controller.category}") String version,
                      @Value("${osl-controller.version}") String category
    ) {
        super("", RESOURCE_NAME, version, RESOURCE_DESCRIPTION, category);
    }

    @Autowired
    public void setStaticDependencies(
        CatalogClient catalogClient,
        SliceManagerService sliceManagerService,
        UeSliceAssociationMapper ueSliceAssociationMapper
    ) {
        ResourceUE.aCatalogClient = catalogClient;
        ResourceUE.sliceManagerService = sliceManagerService;
        ResourceUE.ueSliceAssociationMapper = ueSliceAssociationMapper;
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

        // ue_id is the POST /core/ues/{ue_id}/slice-associations path parameter; it is not part of
        // UeSliceAssociationCreateRequest so it is not covered by ueSliceAssociationMapper.
        ResourceSpecificationCharacteristic ueIdCi = ObjectToOslCharacteristicsMapper.addResourceSpecCharacteristicItemShort(
            "ue_id", DefaultsFactory.getUeId(), null, EValueType.TEXT, "Parameter: ue_id", "", true
        );
        rsc.addResourceSpecificationCharacteristicItem(ueIdCi);

        logger.info("Adding resource specification characteristics from default UE slice association");
        UeSliceAssociationCreateRequest defaultUeSliceAssociation = DefaultsFactory.getUeSliceAssociation();
        for (ResourceSpecificationCharacteristic rchar : ueSliceAssociationMapper.toResourceSpecCharacteristics(defaultUeSliceAssociation)) {
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
     * Kicks off the UE-to-slice association upstream and hands back a {@code resourceUpdate}
     * carrying the asynchronous operation's {@code status.creation.requestId}, which
     * {@code PollingService} polls to completion. The "uses" relationship to the slice resource is
     * added immediately since
     * {@code slice_id} is already known from the characteristics, unlike the old synchronous API
     * which only revealed it in the create response.
     */
    public static ResourceUpdate createResourceLogic(ResourceUpdate resourceUpdate, String resourceid) {
        UeSliceAssociationCreateRequest req;
        String ueId;
        try {
            req = ueSliceAssociationMapper.fromResourceCharacteristics(resourceUpdate.getResourceCharacteristic());
            ueId = ueSliceAssociationMapper.extractUeId(resourceUpdate.getResourceCharacteristic());
        } catch (IllegalArgumentException e) {
            logger.error("Error mapping resource characteristics to UE slice association for resource {}: {}", resourceid, e.getMessage());
            resourceUpdate.setResourceStatus(ResourceStatusType.SUSPENDED);
            return resourceUpdate;
        }

        if (ueId == null) {
            logger.error("No ue_id characteristic found for resource {}", resourceid);
            resourceUpdate.setResourceStatus(ResourceStatusType.SUSPENDED);
            return resourceUpdate;
        }

        Instant startTime = Instant.now();
        UUID requestId = switch (sliceManagerService.associateUeToSlice(ueId, req)) {
            case ApiResult.Success<OperationStatusResponse> s -> s.value().getRequestId();
            default -> null;
        };
        if (requestId == null) {
            logger.error("Slice Manager returned no request id for association of UE {} to slice {}", ueId, req.getSliceId());
            resourceUpdate.setResourceStatus(ResourceStatusType.SUSPENDED);
            return resourceUpdate;
        }

        // the slice resource id is the same as slice_id, by convention (see ResourceSlice#createResourceLogic)
        Resource sliceResource = aCatalogClient.retrieveResourceById(req.getSliceId());
        if (sliceResource != null) {
            ResourceRef ref = new ResourceRef().id(req.getSliceId());
            ref.setName(sliceResource.getName());
            ResourceRelationship sliceRelationship = new ResourceRelationship()
                .resource(ref)
                .relationshipType("uses");
            resourceUpdate.addResourceRelationshipItem(sliceRelationship);
        } else {
            logger.warn("Slice resource {} not found in catalog; skipping 'uses' relationship for UE {}", req.getSliceId(), ueId);
        }

        LifecycleCharacteristics.setCreationRequestId(resourceUpdate, requestId);
        LifecycleCharacteristics.setCreationStart(resourceUpdate, requestId, startTime);
        resourceUpdate.setResourceStatus(ResourceStatusType.RESERVED);
        logger.info("Association of UE {} to slice {} accepted upstream as operation {}; polling for completion",
            ueId, req.getSliceId(), requestId);
        return resourceUpdate;
    }

    public static void createResourceRefsLogic(ResourceUpdate resourceUpdate, String resourceid) {
        // Add reverse relationship (Slice contains UE)
        List<ResourceRelationship> relationshipList = resourceUpdate.getResourceRelationship();

        if (relationshipList == null || relationshipList.isEmpty()) {
            logger.warn("No relationships found in UE resource with ID: {}", resourceid);
            return;
        }

        Optional<ResourceRelationship> ueSliceRelationship = relationshipList.stream().filter(r -> r.getRelationshipType().equals("uses")).findFirst();

        if (ueSliceRelationship.isEmpty()) {
            logger.warn("No 'uses' relationship found in UE resource with ID: {}", resourceid);
            return;
        }

        String sliceId = ueSliceRelationship.get().getResource().getId();

        ResourceUpdate sliceResourceUpdate = new ResourceUpdate();
        ResourceRef ueRef = new ResourceRef().id(resourceid);
        ueRef.setName(resourceUpdate.getName());
        ResourceRelationship ueRelationship = new ResourceRelationship()
            .resource(ueRef)
            .relationshipType("usedBy");
        sliceResourceUpdate.addResourceRelationshipItem(ueRelationship);
        sliceResourceUpdate.addResourceCharacteristicItem(
            new Characteristic()
                .name("lastUpdated")
                .value(new Any(Instant.now().toString())).
                valueType(EValueType.TEXT.getValue())
        );
        logger.info("CALLED RESOURCE SLICE UPDATE");
        aCatalogClient.updateResourceById(sliceId, sliceResourceUpdate);
        logger.info("Added reverse relationship to Slice resource with ID: {}", resourceid);
    }

    /**
     * Kicks off the UE-to-slice disassociation upstream, symmetric with {@link #createResourceLogic}.
     */
    public static ResourceUpdate deleteResourceLogic(ResourceUpdate resourceUpdate, String resourceid) {
        String ueId = ueSliceAssociationMapper.extractUeId(resourceUpdate.getResourceCharacteristic());
        UeSliceAssociationCreateRequest req = ueSliceAssociationMapper.fromResourceCharacteristics(resourceUpdate.getResourceCharacteristic());

        if (ueId == null || req.getSliceId() == null) {
            logger.error("Missing ue_id or slice_id for resource {}; cannot delete the UE association", resourceid);
            resourceUpdate.setResourceStatus(ResourceStatusType.SUSPENDED);
            return resourceUpdate;
        }

        UUID requestId = switch (sliceManagerService.disassociateUeFromSlice(ueId, req.getSliceId())) {
            case ApiResult.Success<OperationStatusResponse> s -> s.value().getRequestId();
            default -> null;
        };
        if (requestId == null) {
            logger.error("Slice Manager returned no request id for disassociation of UE {} from slice {}", ueId, req.getSliceId());
            resourceUpdate.setResourceStatus(ResourceStatusType.SUSPENDED);
            return resourceUpdate;
        }

        LifecycleCharacteristics.setDeletionRequestId(resourceUpdate, requestId);
        logger.info("Disassociation of UE {} from slice {} accepted upstream as operation {}; polling for completion",
            ueId, req.getSliceId(), requestId);
        return resourceUpdate;
    }


}