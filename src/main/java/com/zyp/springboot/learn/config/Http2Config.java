
package com.zyp.springboot.learn.config;

import org.apache.coyote.http2.Http2Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Http2Config {
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> {
            connector.addUpgradeProtocol(new Http2Protocol());
        });
        return factory;
    }


    // HTTP/2 需要 TLS 1.2 或更高版本
    // 强制 HTTP/2，禁用 HTTP/1.1 回退
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> factory.addConnectorCustomizers(connector -> {
            connector.setAttribute("sslEnabledProtocols", "TLSv1.2,TLSv1.3");
            connector.setProperty("allowedProtocols", "h2");
        });
    }
}