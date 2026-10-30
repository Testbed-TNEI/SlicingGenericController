package org.etsi.osl.example.gc.api.resources;

import lombok.Data;
import org.etsi.osl.domain.model.DomainModelDefinition;
import org.etsi.osl.domain.model.ITMFRCM634_ModelTransformer;
import org.etsi.osl.domain.model.ITMFRI639_ModelTransformer;
import org.etsi.osl.example.gc.api.config.CatalogClient;
import org.etsi.osl.tmf.common.model.ELifecycle;
import org.etsi.osl.tmf.common.model.EValueType;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecification;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCreate;
import org.etsi.osl.tmf.ri639.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


@Data
public class ResourceA extends DomainModelDefinition
    implements ITMFRCM634_ModelTransformer, ITMFRI639_ModelTransformer {


    private static final Logger logger = LoggerFactory.getLogger("org.etsi.osl.example.gc");

    public static final String OSL_RSPEC_TYPE = "LogicalResourceSpecification";
    public static final String RESOURCE_NAME = "ResourceA";
    public static final String RESOURCE_DESCRIPTION = "Resource A description";


    private static String paramA = "";

    private static String paramB = "none";

    private static String infoMessage = "none";

    private static String healthStatus = "NA";


    static ThreadController controller = new ThreadController();


    @Autowired
    static CatalogClient aCatalogClient;

    public ResourceA(String version, String category) {
        super("", RESOURCE_NAME, version, RESOURCE_DESCRIPTION, category);
    }


    @Override
    public ResourceSpecificationCreate toRSpecCreate_InitRepo() {

        ResourceSpecificationCreate rsc = new ResourceSpecificationCreate();

        logger.info("ResourceA Catalog Client " + aCatalogClient);

        rsc.setName(this.name);
        rsc.setVersion(this.version);
        rsc.setDescription(this.description);
        rsc.setType(OSL_RSPEC_TYPE);
        rsc.setCategory(this.category);

        rsc.setLifecycleStatus(ELifecycle.ACTIVE.getValue());

//    ResourceSpecificationCharacteristic ci = ObjectToCharacteristicsMapper.addResourceSpecificationCharacteristicItemShort("Kind", this.category,  EValueType.TEXT, this.category , "", true);
//    rsc.addResourceSpecificationCharacteristicItem(ci);
//
//    ci = ObjectToCharacteristicsMapper.addResourceSpecificationCharacteristicItemShort("paramA", paramA, EValueType.TEXT, description , "", true);
//    rsc.addResourceSpecificationCharacteristicItem(ci);
//
//    ci = ObjectToCharacteristicsMapper.addResourceSpecificationCharacteristicItemShort("paramB", paramB, EValueType.TEXT, description , "", true);
//    rsc.addResourceSpecificationCharacteristicItem(ci);
//
//    ci = ObjectToCharacteristicsMapper.addResourceSpecificationCharacteristicItemShort("status.infoMessage", infoMessage, EValueType.TEXT, description , "", true);
//    rsc.addResourceSpecificationCharacteristicItem(ci);
//
//    ci = ObjectToCharacteristicsMapper.addResourceSpecificationCharacteristicItemShort("status.Health", healthStatus,  EValueType.TEXT, description , "", true);
//    rsc.addResourceSpecificationCharacteristicItem(ci);


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
     * example
     */
    public static ResourceUpdate resourceLogic(ResourceUpdate resourceUpdate, String resourceid) {

        logger.info("ResourceA Catalog Client " + aCatalogClient);

        for (Characteristic c : resourceUpdate.getResourceCharacteristic()) {
            if (c.getName().equalsIgnoreCase("paramA")) {
                paramA = c.getValue().getValue();
            }
            if (c.getName().equalsIgnoreCase("paramB")) {
                paramB = c.getValue().getValue();
            }
        }

        infoMessage = "all well " + paramA + ", " + paramB;
        healthStatus = "Healthy";

        resourceUpdate.addResourceCharacteristicItemShort("status.infoMessage", infoMessage, EValueType.TEXT.getValue());
        resourceUpdate.addResourceCharacteristicItemShort("status.Health", healthStatus, EValueType.TEXT.getValue());
        resourceUpdate.addResourceCharacteristicItemShort("status.UUID", UUID.randomUUID().toString(), EValueType.TEXT.getValue());

        if (paramA.equals("start")) {
            controller.startThread(resourceid);
        } else {
            controller.stopThread();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        resourceUpdate.setResourceStatus(ResourceStatusType.AVAILABLE);
        return resourceUpdate;

    }


    public static class ThreadController {

        private Thread workerThread;
        private volatile boolean running = false;
        private String resourceId;

        /**
         * Starts the thread.
         *
         * @param resourceid2
         */
        public synchronized void startThread(String r) {
            this.resourceId = r;
            if (workerThread != null && workerThread.isAlive()) {
                System.out.println("Thread is already running.");
                return;
            }

            running = true; // Set the running flag to true

            workerThread = new Thread(() -> {
                int i = 0;
                while (running) {
                    try {
                        System.out.println("Thread is running...");
                        ResourceUpdate resourceUpdate = new ResourceUpdate();
                        resourceUpdate.addResourceCharacteristicItemShort("status.infoMessage", "thread " + i++, EValueType.TEXT.getValue());

                        logger.info("ResourceA Catalog Client inside threadcontroller" + aCatalogClient);

                        Resource res = aCatalogClient.updateResourceById(resourceId, resourceUpdate);
                        Thread.sleep(1000); // Simulate some work
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted.");
                        Thread.currentThread().interrupt(); // Restore the interrupted status
                    }
                }
                System.out.println("Thread has stopped.");
            });

            workerThread.start();
        }

        /**
         * Stops the thread.
         */
        public synchronized void stopThread() {
            if (workerThread == null || !workerThread.isAlive()) {
                System.out.println("Thread is not running.");
                return;
            }

            ResourceUpdate resourceUpdate = new ResourceUpdate();
            resourceUpdate.addResourceCharacteristicItemShort("status.infoMessage", "thread stopped", EValueType.TEXT.getValue());
            logger.info("ResourceA Catalog Client inside threadcontroller" + aCatalogClient);
            Resource res = aCatalogClient.updateResourceById(resourceId, resourceUpdate);

            running = false; // Set the running flag to false
            workerThread.interrupt(); // Interrupt the thread if it's waiting or sleeping
            try {
                workerThread.join(); // Wait for the thread to finish execution
            } catch (InterruptedException e) {
                System.out.println("Thread stopping interrupted.");
                Thread.currentThread().interrupt(); // Restore the interrupted status
            }
        }
    }

}
