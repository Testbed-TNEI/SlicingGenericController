/*-
 * ========================LICENSE_START=================================
 * org.etsi.osl.tmf.api
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
package org.etsi.osl.example.gc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;


/**
 * For implementing the callback and events, it might be useful to check the DDD pattern:
 * https://www.baeldung.com/spring-data-ddd
 *
 * @author ctranoris
 *
 */
@SpringBootApplication
@EntityScan(basePackages = {"org.etsi.osl.example.gc", "org.etsi.osl.example.gc.api"})

public class ExGCSpringBoot implements CommandLineRunner {

    private static ApplicationContext applicationContext;

    private static final Logger logger = LoggerFactory.getLogger("org.etsi.osl.example.gc");


    @Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    public static void main(String[] args) throws Exception {

        logger.info("=========== STARTING org.etsi.osl.example.gc ==============================");
        applicationContext = new SpringApplication(ExGCSpringBoot.class).run(args);


        // for (String beanName : applicationContext.getBeanDefinitionNames()) {
        // System.out.println(beanName);
        // }
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }

}
