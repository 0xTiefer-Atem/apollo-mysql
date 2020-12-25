package org.springboot.apollo.demo.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@Data
public class MysqlComponent {
    @Value("${spring.datasource.druid.url}")
    private String url;

    @Value("${spring.datasource.druid.username}")
    private String username;

    @Value("${spring.datasource.druid.password}")
    private String password;

    @Value("${spring.datasource.druid.driver-class-name}")
    private String driverClass;

    @Value("${spring.datasource.druid.filter.stat.log.slow-sql}")
    private boolean showSql;

    @Value("${spring.datasource.druid.filter.stat.slow-sql-millis}")
    private int slowSqlMillis;
}
