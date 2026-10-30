package org.etsi.osl.example.gc.api.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.etsi.osl.tmf.common.model.service.ServiceRef;
import org.etsi.osl.tmf.rcm634.model.LogicalResourceSpecification;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecification;
import org.etsi.osl.tmf.rcm634.model.ResourceSpecificationCreate;
import org.etsi.osl.tmf.ri639.model.LogicalResource;
import org.etsi.osl.tmf.ri639.model.Resource;
import org.etsi.osl.tmf.ri639.model.ResourceCreate;
import org.etsi.osl.tmf.ri639.model.ResourceUpdate;
import org.etsi.osl.tmf.scm633.model.ServiceSpecification;
import org.etsi.osl.tmf.scm633.model.ServiceSpecificationCreate;
import org.etsi.osl.tmf.sim638.model.ServiceActionQueueItem;
import org.etsi.osl.tmf.sim638.model.ServiceUpdate;
import org.etsi.osl.tmf.so641.model.ServiceOrder;
import org.etsi.osl.tmf.so641.model.ServiceOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to exchange information with TMF API services
 *
 */
@Service
public class CatalogClient extends RouteBuilder {


    private static final Logger logger = LoggerFactory.getLogger("org.etsi.osl.example.gc");

    @Autowired
    private ProducerTemplate template;


    @Value("${CATALOG_GET_RESOURCESPEC_BY_ID}")
    private String CATALOG_GET_RESOURCESPEC_BY_ID = "";

    @Value("${CATALOG_GET_RESOURCESPEC_BY_NAME_CATEGORY}")
    private String CATALOG_GET_RESOURCESPEC_BY_NAME_CATEGORY = "";


    @Value("${CATALOG_UPDADD_RESOURCESPEC}")
    private String CATALOG_UPDADD_RESOURCESPEC = "";


    @Value("${CATALOG_UPDADD_RESOURCE}")
    private String CATALOG_UPDADD_RESOURCE = "";


    @Value("${CATALOG_UPD_RESOURCE}")
    private String CATALOG_UPD_RESOURCE = "";

    @Value("${CATALOG_GET_RESOURCE_BY_ID}")
    private String CATALOG_GET_RESOURCE_BY_ID = "";

    @Value("${CATALOG_ADD_SERVICESPEC}")
    private String CATALOG_ADD_SERVICESPEC = "";

    @Value("${CATALOG_GET_SERVICESPEC_BY_ID}")
    private String CATALOG_GET_SERVICESPEC_BY_ID = "";

    @Value("${CATALOG_GET_SERVICEORDERS}")
    private String CATALOG_GET_SERVICEORDERS = "";

    @Value("${CATALOG_SERVICE_QUEUE_ITEMS_GET}")
    private String CATALOG_SERVICE_QUEUE_ITEMS_GET = "";

    @Value("${CATALOG_GET_SERVICE_BY_ID}")
    private String CATALOG_GET_SERVICE_BY_ID = "";

    @Value("${CATALOG_GET_SERVICEORDER_BY_ID}")
    private String CATALOG_GET_SERVICEORDER_BY_ID = "";

    @Value("${CATALOG_UPD_SERVICE}")
    private String CATALOG_UPD_SERVICE = "";


    //private ConcurrentHashMap<String, ResourceUpdate> resourcesToBeUpdated = new ConcurrentHashMap<>();


    @Override
    public void configure() throws Exception {


//	  from( "timer://processUpdateResources?period=5000" )
//      .log(LoggingLevel.INFO, log, " process processUpdateResources!")
//      .to("log:DEBUG?showBody=true&showHeaders=true")
//      .bean( "catalogClient", "processUpdateResources()");


    }


    /**
     * get  service spec by id from model via bus
     *
     * @param id
     * @return
     * @throws IOException
     */
    public ResourceSpecification retrieveResourceSpecByNameCategoryVersion(String aName, String aCategory, String aVersion) {
        logger.debug("will retrieve Resource Specification aName=" + aName);

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("aname", aName);
            map.put("acategory", aCategory);
            map.put("aversion", aVersion);
            Object response =
                template.requestBodyAndHeaders(CATALOG_GET_RESOURCESPEC_BY_NAME_CATEGORY, null, map);

            if (!(response instanceof String)) {
                logger.error("Resource Specification object is wrong.");
                return null;
            }
            LogicalResourceSpecification sor = toJsonObj((String) response, LogicalResourceSpecification.class);
            //logger.debug("retrieveSpec response is: " + response);
            return sor;

        } catch (Exception e) {
            logger.error("Cannot retrieve Resource Specification details from catalog. " + e.toString());
        }
        return null;
    }

    /**
     * get  service spec by id from model via bus
     *
     * @param id
     * @return
     * @throws IOException
     */
    public ResourceSpecification retrieveResourceSpec(String specid) {
        logger.debug("will retrieve Resource Specification id=" + specid);

        try {
            Object response = template.
                requestBody(CATALOG_GET_RESOURCESPEC_BY_ID, specid);

            if (!(response instanceof String)) {
                logger.error("Resource Specification object is wrong.");
                return null;
            }
            LogicalResourceSpecification sor = toJsonObj((String) response, LogicalResourceSpecification.class);
            //logger.debug("retrieveSpec response is: " + response);
            return sor;

        } catch (Exception e) {
            logger.error("Cannot retrieve Resource Specification details from catalog. " + e.toString());
        }
        return null;
    }


    public LogicalResourceSpecification createOrUpdateResourceSpecByNameCategoryVersion(ResourceSpecificationCreate s) {
        logger.debug("will createOrUpdateResourceSpecByNameCategoryVersion ");
        logger.debug("s= " + s);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("aname", s.getName());
            map.put("aversion", s.getVersion());
            map.put("acategory", s.getCategory());

            Object response = template.requestBodyAndHeaders(CATALOG_UPDADD_RESOURCESPEC, toJsonString(s), map);

            if (!(response instanceof String)) {
                logger.error("ResourceSpecification  object is wrong.");
            }

            LogicalResourceSpecification rs = toJsonObj((String) response, LogicalResourceSpecification.class);
            return rs;


        } catch (Exception e) {
            logger.error("Cannot create ResourceSpecification");
            e.printStackTrace();
        }
        return null;

    }

    public Resource createOrUpdateResourceByNameCategoryVersion(ResourceCreate s) {
        logger.debug("will createOrUpdateResourceByNameVersion a Resource ");
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("aname", s.getName());
            map.put("aversion", s.getResourceVersion());
            map.put("acategory", s.getCategory());

            Object response = template.requestBodyAndHeaders(CATALOG_UPDADD_RESOURCE, toJsonString(s), map);

            if (!(response instanceof String)) {
                logger.error("Resource  object is wrong.");
            }

            logger.debug(response.toString());
            try {
                LogicalResource rs = toJsonObj((String) response, LogicalResource.class);
                return rs;
            } catch (Exception e) {
                logger.error("Cannot create LogicalResource");
                e.printStackTrace();
            }

            try {
                Resource rs = toJsonObj((String) response, Resource.class);
                return rs;
            } catch (Exception e) {
                logger.error("Cannot create as Resource");
                e.printStackTrace();
            }


        } catch (Exception e) {
            logger.error("Cannot create Resource");
            e.printStackTrace();
        }
        return null;

    }

    public org.etsi.osl.tmf.sim638.model.Service retrieveServiceRelatedToResourceId(String oslResourceId) {
        logger.info("will retrieve service orders related related to resource with id=" + oslResourceId);

        try {
            Object response = template.requestBody(CATALOG_GET_SERVICEORDERS, new HashMap<String, String>());

            if (!(response instanceof String)) {
                logger.error("Resource object is wrong.");
                return null;
            }

            logger.info("retrieveResource response is: " + response);

            String[] serviceOrderIds = toJsonObj((String) response, String[].class);
            for (String serviceOrderId : serviceOrderIds) {
                if (serviceOrderId == null)
                    continue;
                ServiceOrder serviceOrder = this.retrieveServiceOrder(serviceOrderId);
                if (serviceOrder == null)
                    continue;
                for (ServiceOrderItem orderItem : serviceOrder.getOrderItem()) {
                    for (ServiceRef supportingService : orderItem.getService().getSupportingService()) {
                        org.etsi.osl.tmf.sim638.model.Service service = this.retrieveService(supportingService.getId());
                        if (service == null)
                            continue;
                        if (service.getSupportingResource().stream().filter(r -> r.getId().equals(oslResourceId)).findAny().orElse(null) != null)
                            return service;
                    }
                }
            }

        } catch (Exception e) {
            logger.error("Cannot retrieve Resource details from catalog. " + e.toString());
        }
        return null;
    }

    /**
     * get  service order by id from model via bus
     *
     * @param id
     * @return
     * @throws IOException
     */
    public ServiceOrder retrieveServiceOrder(String orderid) {
        logger.info("will retrieve Service Order from catalog orderid=" + orderid);
        try {
            Object response = template.
                requestBody(CATALOG_GET_SERVICEORDER_BY_ID, orderid);

            if (!(response instanceof String)) {
                logger.error("Service Order object is wrong.");
                return null;
            }
            logger.debug("retrieveServiceOrder response is: " + response);
            ServiceOrder sor = toJsonObj((String) response, ServiceOrder.class);

            return sor;

        } catch (Exception e) {
            logger.error("Cannot retrieve Service Order details from catalog. " + e.toString());
        }
        return null;
    }


    /**
     * Ger service instance via bus
     *
     * @param serviceID
     * @return
     */
    public org.etsi.osl.tmf.sim638.model.Service retrieveService(String serviceID) {
        logger.info("will retrieve Service instance from catalog serviceID=" + serviceID);
        try {
            Object response = template.
                requestBody(CATALOG_GET_SERVICE_BY_ID, serviceID);

            if (!(response instanceof String)) {
                logger.error("Service object is wrong.");
                return null;
            }
            org.etsi.osl.tmf.sim638.model.Service serviceInstance = toJsonObj((String) response, org.etsi.osl.tmf.sim638.model.Service.class);
            //logger.debug("retrieveService response is: " + response);
            return serviceInstance;

        } catch (Exception e) {
            logger.error("Cannot retrieve Service details from catalog. " + e.toString());
        }
        return null;
    }


    public List<ServiceActionQueueItem> retrieveServiceQueueItems() {
        logger.info("will retrieve Service QueueItems from repository");
        try {

            Object response = template.
                requestBody(CATALOG_SERVICE_QUEUE_ITEMS_GET, "");

            if (!(response instanceof String)) {
                logger.error("List  object is wrong.");
                return null;
            }
            //String[] sor = toJsonObj( (String)response, String[].class );

            ServiceActionQueueItem[] sor = toJsonObj((String) response, ServiceActionQueueItem[].class);
            logger.info("retrieveServiceQueueItems response is: " + response);

//			return asList(sor);
            return Arrays.asList(sor);

        } catch (Exception e) {
            logger.error("Cannot retrieve Listof Service QueueItems . " + e.toString());
        }
        return null;
    }

    /**
     * @param serviceId
     * @param s
     * @param triggerServiceActionQueue is a cryptic thing. However it is used as follows: if FALSE, to just update the service status in catalog without further taking any action.
     *                                  if TRUE then the ServiceUpdate will trigger a ServiceActionQueue to further process the update. So this is needed to avoid these kinds of deadlocks
     * @return
     */
    public org.etsi.osl.tmf.sim638.model.Service updateService(String serviceId, ServiceUpdate s, boolean triggerServiceActionQueue) {
        logger.info("will update Service : " + serviceId);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("serviceid", serviceId);
            map.put("triggerServiceActionQueue", triggerServiceActionQueue);

            Object response = template.requestBodyAndHeaders(CATALOG_UPD_SERVICE, toJsonString(s), map);

            if (!(response instanceof String)) {
                logger.error("Service Instance object is wrong.");
            }

            org.etsi.osl.tmf.sim638.model.Service serviceInstance = toJsonObj((String) response, org.etsi.osl.tmf.sim638.model.Service.class);
            //logger.debug("createService response is: " + response);
            return serviceInstance;


        } catch (Exception e) {
            logger.error("Cannot update Service: " + serviceId + ": " + e.toString());
        }
        return null;

    }


    private <T> T toJsonObj(String content, Class<T> valueType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(content, valueType);
    }

    private String toJsonString(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }


    public Resource updateResourceById(String oslResourceId, ResourceUpdate rs) {


        logger.debug("will update Resource : " + oslResourceId);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("resourceId", oslResourceId);
            map.put("triggerServiceActionQueue", false);

            Object response = template.requestBodyAndHeaders(CATALOG_UPD_RESOURCE, toJsonString(rs), map);

            if (!(response instanceof String)) {
                logger.error("Service Instance object is wrong.");
            }

            LogicalResource resourceInstance = toJsonObj((String) response, LogicalResource.class);
            //logger.debug("createService response is: " + response);
            return resourceInstance;


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Cannot update Service: " + oslResourceId + ": " + e.toString());
        }
        return null;
    }

    public Resource retrieveResourceById(String oslResourceId) {
        logger.debug("will retrieve Resource id=" + oslResourceId);

        try {
            Object response = template.requestBody(CATALOG_GET_RESOURCE_BY_ID, oslResourceId);

            if (!(response instanceof String)) {
                logger.error("Resource object is wrong.");
                return null;
            }
            LogicalResource resourceInstance = toJsonObj((String) response, LogicalResource.class);
            // logger.debug("retrieveResource response is: " + response);
            return resourceInstance;

        } catch (Exception e) {
            logger.error("Cannot retrieve Resource details from catalog. " + e.toString());
        }
        return null;
    }

    /**
     * get service spec by id from model via bus
     *
     * @param specid
     * @return
     */
    public ServiceSpecification retrieveServiceSpec(String specid) {
        logger.debug("will retrieve Service Specification id=" + specid);

        try {
            Object response = template.
                requestBody(CATALOG_GET_SERVICESPEC_BY_ID, specid);

            if (!(response instanceof String)) {
                logger.error("Service Specification object is wrong.");
                return null;
            }
            ServiceSpecification ss = toJsonObj((String) response, ServiceSpecification.class);
            return ss;

        } catch (Exception e) {
            logger.error("Cannot retrieve Service Specification details from catalog. " + e.toString());
        }
        return null;
    }

    /**
     * Creates or updates a Resource Facing Service Specification (RFSS) based on a resource
     *
     * @param lrs The resource to create the RFSS from
     * @return ServiceSpecification The created or updated RFSS
     */
    public ServiceSpecification createOrUpdateRFSSSpecByNameCategoryVersion(ServiceSpecificationCreate ssc) {


        try {
            // Create ServiceSpecificationCreate from the Resource


            // Prepare headers with name, version, and category
            Map<String, Object> map = new HashMap<>();
            // map.put("aname", serviceSpec.getName());
            // map.put("aversion", serviceSpec.getVersion());

            // Send to ActiveMQ queue
            Object response = template.requestBodyAndHeaders(
                CATALOG_ADD_SERVICESPEC,
                toJsonString(ssc),
                map
            );

            if (!(response instanceof String)) {
                logger.error("ServiceSpecification object response is wrong.");
                return null;
            }

            // Convert response to ServiceSpecification
            ServiceSpecification ss = toJsonObj((String) response, ServiceSpecification.class);
            logger.debug("Created/Updated RFSS with id: " + ss.getId());
            return ss;

        } catch (Exception e) {
            logger.error("Cannot create or update ServiceSpecification (RFSS)");
            e.printStackTrace();
        }
        return null;
    }


//  public void updateResourceById(String oslResourceId, ResourceUpdate rs) {
//
//    resourcesToBeUpdated.put(oslResourceId, rs);
//   
//    
//  }

//  public void processUpdateResources() {
//    
//    resourcesToBeUpdated.forEach( (oslResourceId, rs) -> {
//      
//      logger.info("will update Resource : " + oslResourceId );
//      try {
//          Map<String, Object> map = new HashMap<>();
//          map.put("resourceId", oslResourceId );
//          map.put("triggerServiceActionQueue", false );
//          String json = toJsonString(rs);
//          Object response = template.requestBodyAndHeaders( CATALOG_UPD_RESOURCE, json, map);
//
//          if ( !(response instanceof String)) {
//              logger.error("Service Instance object is wrong.");
//          }
//
//          //LogicalResource resourceInstance = toJsonObj( (String)response, LogicalResource.class); 
//          //logger.debug("createService response is: " + response);
//          //return resourceInstance;
//          
//          resourcesToBeUpdated.remove(oslResourceId);
//          
//
//          logger.info("Updated successfully Resource : " + oslResourceId );
//      }catch (Exception e) {
//        e.printStackTrace();
//          logger.error("Cannot update Resource: " + oslResourceId + ": " + e.toString());
//      }
//      
//      
//      
//    });
//  }

}
