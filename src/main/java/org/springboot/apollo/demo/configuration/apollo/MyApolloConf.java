package org.springboot.apollo.demo.configuration.apollo;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
@Data
public class MyApolloConf {
    @Value("${apollo.username}")
    private String username;

    @Value("${apollo.password}")
    private String password;

    @Value("${apollo.url}")
    private String url;


}
