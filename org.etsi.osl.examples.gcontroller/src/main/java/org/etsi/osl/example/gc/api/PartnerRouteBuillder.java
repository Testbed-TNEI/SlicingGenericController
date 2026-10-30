package org.etsi.osl.example.gc.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.etsi.osl.example.gc.api.services.ResourceRepoService;
import org.etsi.osl.tmf.ri639.model.ResourceCreate;
import org.etsi.osl.tmf.ri639.model.ResourceUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Configuration
@Component
public class PartnerRouteBuillder extends RouteBuilder {

    private static final Logger logger = LoggerFactory.getLogger("org.etsi.osl.example.gc");

    @Value("${spring.application.name}")
    private String compname;

    @Value("${osl-controller.category}")
    private String compcategory;

    @Value("${osl-controller.version}")
    private String compversion;


    @Autowired
    private ProducerTemplate template;

    @Autowired
    ResourceRepoService resourceRepoService;

    @Override
    public void configure() throws Exception {


        String EVENT_CREATE = "jms:queue:CREATE/" + compcategory + "/" + compversion;

        String EVENT_UPDATE = "jms:queue:UPDATE/" + compcategory + "/" + compversion;

        String EVENT_DELETE = "jms:queue:DELETE/" + compcategory + "/" + compversion;

        from(EVENT_CREATE)
            .log(LoggingLevel.INFO, log, EVENT_CREATE + " message received!")
            .to("log:DEBUG?showBody=true&showHeaders=true").unmarshal()
            .json(JsonLibrary.Jackson, ResourceCreate.class, true)
            .bean(resourceRepoService, "createResource( ${headers}, ${body} )")
            .marshal().json(JsonLibrary.Jackson)
            .convertBodyTo(String.class);

        from(EVENT_UPDATE)
            .log(LoggingLevel.INFO, log, EVENT_UPDATE + " message received!")
            .to("log:DEBUG?showBody=true&showHeaders=true").unmarshal()
            .json(JsonLibrary.Jackson, ResourceUpdate.class, true)
            .bean(resourceRepoService, "updateResource( ${headers},${body} )")
            .marshal().json(JsonLibrary.Jackson)
            .convertBodyTo(String.class);
        ;

        from(EVENT_DELETE)
            .log(LoggingLevel.INFO, log, EVENT_DELETE + " message received!")
            .to("log:DEBUG?showBody=true&showHeaders=true").unmarshal()
            .json(JsonLibrary.Jackson, ResourceUpdate.class, true)
            .bean(resourceRepoService, "deleteResource( ${headers}, ${body} )")
            .marshal().json(JsonLibrary.Jackson)
            .convertBodyTo(String.class);
        ;


    }

    static <T> T toJsonObj(String content, Class<T> valueType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(content, valueType);
    }

    static String toJsonString(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }


}
