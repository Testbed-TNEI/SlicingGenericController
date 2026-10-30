package org.etsi.osl.example.gc.api.services;

import jakarta.annotation.PreDestroy;
import org.etsi.osl.example.gc.api.config.CatalogClient;
import org.etsi.osl.example.gc.api.resources.ResourceSlice;
import org.etsi.osl.example.gc.api.resources.ResourceUE;
import org.etsi.osl.tmf.common.model.Any;
import org.etsi.osl.tmf.common.model.EValueType;
import org.etsi.osl.tmf.rcm634.model.LogicalResourceSpecification;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationRef;
import org.etsi.osl.tmf.scm633.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author ctranoris
 */
@Service
public class BootstrapService {

    private static final Logger logger = LoggerFactory.getLogger("org.etsi.osl.example.gc");

    @Autowired
    CatalogClient catalogClient;

    @Value("${osl-controller.category}")
    private String compcategory;

    @Value("${osl-controller.version}")
    private String compversion;


    /**
     * This one is executed when the cridge service application starts
     * <p>
     * It creates here a default model in OSL TMF
     * Create any related Logical resource specifications
     *
     * @param event
     * @throws Exception
     */
    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationEvent() throws Exception {

        // TODO: check if doesn't try to delete it multiple times (when for instance deleting network slice)
        // TODO: dldeterministiccomm and operational_state have values just NOT_SUPPORTED or ENABLED in both options
        // TODO: it eventually bugs out on the CFS for some reason <related_to_previous_topic>
        // TODO: CFS immutable characteristics rollbacking (LCM RULES)

//		ResourceA rspecFed = new ResourceA( compversion, compcategory);
//		LogicalResourceSpecification lrs = catalogClient.createOrUpdateResourceSpecByNameCategoryVersion(rspecFed.toRSpecCreate_InitRepo());

        logger.info("STARTING APPLICATION EVENT");
//

        // Additional characteristic specs
        // TODO: isUpdating not needed in RFS
        ServiceSpecCharacteristic isUpdatingServiceSpecCharacteristic = new ServiceSpecCharacteristic();
        isUpdatingServiceSpecCharacteristic.setName("isUpdating");
        isUpdatingServiceSpecCharacteristic.setConfigurable(false);
        isUpdatingServiceSpecCharacteristic.setValueType("BOOLEAN");
        isUpdatingServiceSpecCharacteristic.addServiceSpecCharacteristicValueItem(
            new ServiceSpecCharacteristicValue().value(new Any("false")).isDefault(true)
        );

        // Slice Resource + Ref
        ResourceSlice rspecFed2 = new ResourceSlice(compversion, compcategory);
        LogicalResourceSpecification lsr2 = catalogClient.createOrUpdateResourceSpecByNameCategoryVersion(rspecFed2.toRSpecCreate_InitRepo());
        logger.info(
            "LOG lsr2 != null: {}, id: {}, name: {}, version: {}",
            lsr2 != null,
            lsr2 != null ? lsr2.getId() : null,
            lsr2 != null ? lsr2.getName() : null,
            lsr2 != null ? lsr2.getVersion() : null
        );
        ResourceSpecificationRef resourceSpecificationRef = new ResourceSpecificationRef().id(lsr2.getId());
        resourceSpecificationRef.setName(lsr2.getName());
        resourceSpecificationRef.setVersion(lsr2.getVersion());
        resourceSpecificationRef.setId(lsr2.getId());

        // Slice RFSS + Ref
        ServiceSpecificationCreate serviceSpecSlice = new ServiceSpecificationCreate();
        serviceSpecSlice.setName(String.format("%sRFS", "Slice"));
        serviceSpecSlice.setIsBundle(false);
        serviceSpecSlice.setVersion(lsr2.getVersion());
        serviceSpecSlice.setDescription(String.format("RFSS created from Resource: %s", lsr2.getName()));
        serviceSpecSlice.setType("ResourceFacingServiceSpecification");
        serviceSpecSlice.setResourceSpecification(List.of(resourceSpecificationRef));
        ServiceSpecification createdServiceSpecSlice = catalogClient.createOrUpdateRFSSSpecByNameCategoryVersion(serviceSpecSlice);
        ServiceSpecRelationship serviceSliceSpecRef = new ServiceSpecRelationship().id(createdServiceSpecSlice.getId());
        serviceSliceSpecRef.setName(createdServiceSpecSlice.getName());
        serviceSliceSpecRef.setRelationshipType("composedOf");

        // Slice CFSS of Slice RFSS
        List<String> excludedCharacteristicsSliceCFS = List.of("SSPEC_GRAPH_NOTATION");
        ServiceSpecificationCreate serviceSpecSliceCFSS = new ServiceSpecificationCreate();
        serviceSpecSliceCFSS.setName("SliceCFS");
        serviceSpecSliceCFSS.setIsBundle(true);
        serviceSpecSliceCFSS.setVersion(compversion);
        serviceSpecSliceCFSS.setDescription(String.format("CFSS created from RFSS: %s", createdServiceSpecSlice.getName()));
        serviceSpecSliceCFSS.setType("CustomerFacingServiceSpecification");
        serviceSpecSliceCFSS.setServiceSpecRelationship(List.of(serviceSliceSpecRef));
        serviceSpecSliceCFSS.setServiceSpecCharacteristic(
            createdServiceSpecSlice.getServiceSpecCharacteristic()
                .stream()
                .filter(c -> !excludedCharacteristicsSliceCFS.contains(c.getName()))
                .map(c -> {
                    // copy: the RFS characteristics are shared entities, renaming them in place
                    // leaks back into the RFS (and into the bundle built further below)
                    ServiceSpecCharacteristic copy = new ServiceSpecCharacteristic(c);
                    if (c.getName().equals("id")) copy.setConfigurable(false);
                    copy.setName("Slice:" + c.getName());
                    return copy;
                })
                .toList()
        );
        catalogClient.createOrUpdateRFSSSpecByNameCategoryVersion(serviceSpecSliceCFSS);

        // UE Resource + Ref
        ResourceUE rspecFed3 = new ResourceUE(compversion, compcategory);
        LogicalResourceSpecification lsr3 = catalogClient.createOrUpdateResourceSpecByNameCategoryVersion(rspecFed3.toRSpecCreate_InitRepo());
        ResourceSpecificationRef resourceSpecRef = new ResourceSpecificationRef().id(lsr3.getId());
        resourceSpecRef.setName(lsr3.getName());
        resourceSpecRef.setVersion(lsr3.getVersion());
        resourceSpecRef.setId(lsr3.getId());

        // UE RFSS + Ref
        ServiceSpecificationCreate serviceSpecUE = new ServiceSpecificationCreate();
        serviceSpecUE.setName(String.format("%sRFS", "UE"));
        serviceSpecUE.setIsBundle(false);
        serviceSpecUE.setVersion(lsr3.getVersion());
        serviceSpecUE.setDescription(String.format("RFSS created from Resource: %s", lsr3.getName()));
        serviceSpecUE.setType("ResourceFacingServiceSpecification");
        serviceSpecUE.setResourceSpecification(List.of(resourceSpecRef));
        ServiceSpecification createdServiceSpecUE = catalogClient.createOrUpdateRFSSSpecByNameCategoryVersion(serviceSpecUE);
        ServiceSpecRelationship serviceUESpecRef = new ServiceSpecRelationship().id(createdServiceSpecUE.getId());
        serviceUESpecRef.setName(createdServiceSpecUE.getName());
        serviceUESpecRef.setRelationshipType("composedOf");

        // UE CFSS of UE RFSS
//		ServiceSpecificationCreate serviceSpecUECFSS = new ServiceSpecificationCreate();
//		serviceSpecUECFSS.setName(String.format("CFSS-%s", createdServiceSpecUE.getName()));
//		serviceSpecUECFSS.setIsBundle(true);
//		serviceSpecUECFSS.setVersion(createdServiceSpecUE.getVersion());
//		serviceSpecUECFSS.setDescription(String.format("CFSS created from RFSS: %s", createdServiceSpecUE.getName()));
//		serviceSpecUECFSS.setType("CustomerFacingServiceSpecification");
//		serviceSpecUECFSS.setServiceSpecRelationship(List.of(serviceUESpecRef));
//		serviceSpecUECFSS.setServiceSpecCharacteristic(
//			createdServiceSpecUE.getServiceSpecCharacteristic()
//				.stream()
//				.filter(serviceSpecCharacteristic -> !serviceSpecCharacteristic.getName().equals("SSPEC_GRAPH_NOTATION"))
//				.toList()
//		);
//		catalogClient.createOrUpdateRFSSSpecByNameCategoryVersion(serviceSpecUECFSS);

        // Bundle of Slice and UEs RFSS
        // Both - SSPEC_GRAPH_NOTATION, Kind; Slice - id; UE - slice;
        List<String> excludedCharacteristics = List.of("SSPEC_GRAPH_NOTATION", "Kind");
        List<String> ueExcludedCharacteristics = List.of("slice_id", "snssai");

        ServiceSpecCharacteristic capifEnabledServiceSpecCharacteristic = new ServiceSpecCharacteristic();
        capifEnabledServiceSpecCharacteristic.setName("capif_enabled");
        capifEnabledServiceSpecCharacteristic.setConfigurable(true);
        capifEnabledServiceSpecCharacteristic.setValueType("BOOLEAN");
        capifEnabledServiceSpecCharacteristic.addServiceSpecCharacteristicValueItem(
            new ServiceSpecCharacteristicValue().value(new Any("true")).isDefault(true)
        );

        ServiceSpecCharacteristic ccamBrokerBackendServiceSpecCharacteristic = new ServiceSpecCharacteristic();
        ccamBrokerBackendServiceSpecCharacteristic.setName("PoC:ccam_broker___backend");
        ccamBrokerBackendServiceSpecCharacteristic.setConfigurable(true);
        ccamBrokerBackendServiceSpecCharacteristic.setValueType(EValueType.TEXT.getValue());
        ccamBrokerBackendServiceSpecCharacteristic.addServiceSpecCharacteristicValueItem(
            new ServiceSpecCharacteristicValue().value(new Any("mock")).isDefault(true)
        );
        ccamBrokerBackendServiceSpecCharacteristic.addServiceSpecCharacteristicValueItem(
            new ServiceSpecCharacteristicValue().value(new Any("mqtt")).isDefault(false)
        );

        ServiceSpecCharacteristic crashInferenceServerUrlServiceSpecCharacteristic = new ServiceSpecCharacteristic();
        crashInferenceServerUrlServiceSpecCharacteristic.setName("PoC:crash_inference___inference_server_url");
        crashInferenceServerUrlServiceSpecCharacteristic.setConfigurable(true);
        crashInferenceServerUrlServiceSpecCharacteristic.setValueType(EValueType.TEXT.getValue());
        crashInferenceServerUrlServiceSpecCharacteristic.addServiceSpecCharacteristicValueItem(
            new ServiceSpecCharacteristicValue().value(new Any("http://10.0.0.1:8888")).isDefault(true)
        );

        ServiceSpecCharacteristic netApisApiServiceSpecCharacteristic = new ServiceSpecCharacteristic();
        netApisApiServiceSpecCharacteristic.setName("PoC:net_apis___api");
        netApisApiServiceSpecCharacteristic.setConfigurable(true);
        netApisApiServiceSpecCharacteristic.setValueType(EValueType.TEXT.getValue());
        netApisApiServiceSpecCharacteristic.addServiceSpecCharacteristicValueItem(
            new ServiceSpecCharacteristicValue().value(new Any("nef")).isDefault(true)
        );
        netApisApiServiceSpecCharacteristic.addServiceSpecCharacteristicValueItem(
            new ServiceSpecCharacteristicValue().value(new Any("camara")).isDefault(false)
        );

        ServiceSpecCharacteristic netApisAuthModeServiceSpecCharacteristic = new ServiceSpecCharacteristic();
        netApisAuthModeServiceSpecCharacteristic.setName("PoC:net_apis___auth_mode");
        netApisAuthModeServiceSpecCharacteristic.setConfigurable(true);
        netApisAuthModeServiceSpecCharacteristic.setValueType(EValueType.TEXT.getValue());
        netApisAuthModeServiceSpecCharacteristic.addServiceSpecCharacteristicValueItem(
            new ServiceSpecCharacteristicValue().value(new Any("capif")).isDefault(true)
        );
        netApisAuthModeServiceSpecCharacteristic.addServiceSpecCharacteristicValueItem(
            new ServiceSpecCharacteristicValue().value(new Any("other")).isDefault(false)
        );

        List<ServiceSpecCharacteristic> pocServiceSpecCharacteristics = List.of(
            ccamBrokerBackendServiceSpecCharacteristic,
            crashInferenceServerUrlServiceSpecCharacteristic,
            netApisApiServiceSpecCharacteristic,
            netApisAuthModeServiceSpecCharacteristic
        );

        // Additional external service specs related to the bundle
        Map<String, String> additionalRelatedServiceSpecIds = new LinkedHashMap<>();
        additionalRelatedServiceSpecIds.put("Grafana and Poller", "92db96ec-cf6f-4c31-8c8b-f9e754956c7d");
        additionalRelatedServiceSpecIds.put("CAPIF RFS", "40ceb0bd-cb08-44af-885a-a162c5c53651");
        additionalRelatedServiceSpecIds.put("NEF RFS", "06c7d1af-d39e-46a9-a9c4-88930115f778");
        additionalRelatedServiceSpecIds.put("CAMARA RFS", "e2aa99a3-0020-4737-8422-beece158638f");
        additionalRelatedServiceSpecIds.put("Vehicle Crash Detection PoC RFS", "15f37f31-fca1-4548-a4fc-48996544fd2b");

        List<ServiceSpecRelationship> additionalServiceSpecRelationships = additionalRelatedServiceSpecIds.entrySet()
            .stream()
            .map(entry -> {
                ServiceSpecification relatedSpec = catalogClient.retrieveServiceSpec(entry.getValue());
                ServiceSpecRelationship ref = new ServiceSpecRelationship().id(entry.getValue());
                ref.setName(relatedSpec != null ? relatedSpec.getName() : entry.getKey());
                ref.setRelationshipType("composedOf");
                return ref;
            })
            .toList();

        ServiceSpecificationCreate serviceSpecBundle = new ServiceSpecificationCreate();
        serviceSpecBundle.setName("SliceAndUEBundleCFS");
        serviceSpecBundle.setIsBundle(true);
        serviceSpecBundle.setVersion(compversion);
        serviceSpecBundle.setDescription(String.format("Bundle created from Service Specs: %s %s", createdServiceSpecSlice.getName(), createdServiceSpecUE.getName()));
        serviceSpecBundle.setType("CustomerFacingServiceSpecification");
        serviceSpecBundle.setServiceSpecRelationship(
            Stream.concat(
                Stream.of(serviceSliceSpecRef, serviceUESpecRef),
                additionalServiceSpecRelationships.stream()
            ).toList()
        );
        serviceSpecBundle.setServiceSpecCharacteristic(
            Stream.concat(
                Stream.concat(
                    createdServiceSpecSlice.getServiceSpecCharacteristic()
                        .stream()
                        .filter(c -> !excludedCharacteristics.contains(c.getName()))
                        .map(c -> {
                            ServiceSpecCharacteristic copy = new ServiceSpecCharacteristic(c);
                            copy.setName("Slice:" + c.getName());
                            return copy;
                        }),
                    createdServiceSpecUE.getServiceSpecCharacteristic()
                        .stream()
                        .filter(c -> !excludedCharacteristics.contains(c.getName()))
                        .filter(c -> !ueExcludedCharacteristics.contains(c.getName()))
                        .map(c -> {
                            ServiceSpecCharacteristic copy = new ServiceSpecCharacteristic(c);
                            copy.setName("UE:" + c.getName());
                            return copy;
                        })
                ),
                Stream.concat(
                    Stream.of(capifEnabledServiceSpecCharacteristic),
                    pocServiceSpecCharacteristics.stream()
                )
            ).toList()
        );


        catalogClient.createOrUpdateRFSSSpecByNameCategoryVersion(serviceSpecBundle);

    }


    @PreDestroy
    private void stop() {
        logger.info("Closing due to ContextStoppedEvent ");
    }
}
