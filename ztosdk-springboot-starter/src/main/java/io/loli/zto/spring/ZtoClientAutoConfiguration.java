package io.loli.zto.spring;

import io.loli.zto.ZtoClientProperties;
import io.loli.zto.client.ZtoApiClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZtoClientAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "zto")
    public ZtoClientProperties ztoClientProperties() {
        return new ZtoClientProperties();
    }

    @Bean
    public ZtoApiClient ztoApiClient() {
        return new ZtoApiClient(ztoClientProperties());
    }

}
