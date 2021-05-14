/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns;

import cn.stylefeng.roses.core.config.MybatisDataSourceAutoConfiguration;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringBoot方式启动类
 *
 * @author stylefeng
 * @Date 2017/5/21 12:06
 * wzctest
 */
@SpringBootApplication(exclude = {MybatisDataSourceAutoConfiguration.class})
@EnableScheduling
public class GunsApplication {

    private final static Logger logger = LoggerFactory.getLogger(GunsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GunsApplication.class, args);
        logger.info(GunsApplication.class.getSimpleName() + " is success!");
    }

    /*@Bean
    public ServletWebServerFactory servletContainer() {

        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {

            @Override
            protected void postProcessContext(Context context) {

                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
        return tomcat;
    }*/

    /**
     * 让我们的应用支持HTTP是个好想法，但是需要重定向到HTTPS，
     * 但是不能同时在application.properties中同时配置两个connector，
     * 所以要以编程的方式配置HTTP connector，然后重定向到HTTPS connector
     * @return Connector*/

    /*private Connector initiateHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(80); // http端口
        connector.setSecure(false);
        connector.setRedirectPort(443); // application.properties中配置的https端口
        return connector;
    }*/
}
