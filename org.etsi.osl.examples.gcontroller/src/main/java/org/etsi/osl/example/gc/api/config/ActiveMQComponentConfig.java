/*-
 * ========================LICENSE_START=================================
 * org.etsi.osl.bugzilla
 * %%
 * Copyright (C) 2019 openslice.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */
package org.etsi.osl.example.gc.api.config;

import jakarta.jms.ConnectionFactory;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ctranoris
 *
 */
@Configuration
public class ActiveMQComponentConfig {

    @Bean(name = "activemq")
    public ActiveMQComponent createComponent(ConnectionFactory factory) {
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setConnectionFactory(factory);
        activeMQComponent.setRequestTimeout(0); // 0 = wait indefinitely (no timeout)
        return activeMQComponent;
    }

    // Overrides the auto-configured jms component to disable request-reply timeout.
    // All jms:queue: endpoints (CatalogClient, PartnerRouteBuillder) use this component.
    @Bean(name = "jms")
    public JmsComponent jmsComponent(ConnectionFactory factory) {
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(factory);
        jmsComponent.setRequestTimeout(0); // 0 = wait indefinitely (no timeout)
        return jmsComponent;
    }
}
