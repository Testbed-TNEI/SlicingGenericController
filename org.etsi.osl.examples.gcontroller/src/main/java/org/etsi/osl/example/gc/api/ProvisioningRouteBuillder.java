package org.etsi.osl.example.gc.api;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.etsi.osl.example.gc.api.services.PollingService;
import org.etsi.osl.tmf.ri639.model.ResourceUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Consumes the provisioning queue that {@link PartnerRouteBuillder} hands work to.
 *
 * <p>This is where {@link PollingService} polls the Slice Manager's async operations to completion.
 * It is deliberately separate from the OSOM-facing routes so that a multi-minute slice creation never
 * delays the reply OSOM is waiting 20 seconds for.
 *
 * <p>The message stays unacknowledged until the resource status writeback has landed, so a crash
 * mid-polling results in redelivery rather than a resource stuck in RESERVED. Consumers are
 * concurrent for throughput; per-resource ordering comes from the {@code JMSXGroupID} the producer
 * sets, not from limiting consumers.
 *
 * <p>That acknowledgement is deliberately CLIENT_ACKNOWLEDGE rather than a JMS transaction. Every
 * path through {@link PollingService} ends at {@code CatalogClient.updateResourceById}, which
 * is a synchronous JMS request-reply. Under {@code transacted=true} Spring binds that send to the
 * consumer's transactional session, so the request is not delivered until the transaction commits,
 * while the commit waits on a reply that can never arrive — a deadlock, made permanent by the
 * {@code requestTimeout=0} in {@code ActiveMQComponentConfig}. CLIENT_ACKNOWLEDGE gives the same
 * redelivery guarantee with no transaction for the request-reply to enlist in: Spring's
 * {@code AbstractMessageListenerContainer} acknowledges only after the listener returns, and calls
 * {@code Session.recover()} when it throws.
 */
@Configuration
@Component
public class ProvisioningRouteBuillder extends RouteBuilder {

    @Autowired
    PollingService pollingService;

    @Override
    public void configure() throws Exception {

        // Bounded retries. Once exhausted the message is marked handled (and so acknowledged), and
        // the resource is marked SUSPENDED so OSL's poll loop can settle instead of spinning.
        onException(Exception.class)
            .maximumRedeliveries(3)
            .redeliveryDelay(15000)
            .backOffMultiplier(2)
            .useExponentialBackOff()
            .retryAttemptedLogLevel(LoggingLevel.WARN)
            .handled(true)
            .bean(pollingService, "handleFailure( ${headers}, ${body}, ${exception} )");

        from(pollingService.provisioningQueue()
            + "?acknowledgementModeName=CLIENT_ACKNOWLEDGE&concurrentConsumers=5")
            .routeId("gc-provisioning")
            // Bracket syntax is required: the header name contains dots, which Simple would
            // otherwise read as an OGNL path on a header called "org".
            .log(LoggingLevel.INFO, log, "Provisioning request received for resource ${header['" + PollingService.HEADER_RESOURCE_ID + "']}")
            .unmarshal()
            .json(JsonLibrary.Jackson, ResourceUpdate.class, true)
            .bean(pollingService, "handle( ${headers}, ${body} )");

    }

}