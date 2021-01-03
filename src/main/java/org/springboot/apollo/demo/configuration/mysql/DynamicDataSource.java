package org.springboot.apollo.demo.configuration.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import org.springboot.apollo.demo.component.MysqlComponent;
import org.springboot.apollo.demo.component.SpringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author： WangQian
 * @date： 2020/7/29 下午 6:30
 */


public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> dataSourceKey = ThreadLocal.withInitial(() -> "defaultDataSource");

    public static Map<Object, Object> dataSourcesMap = new ConcurrentHashMap<>(10);

    static {
        dataSourcesMap.put("defaultDataSource", SpringUtils.getBean("defaultDataSource"));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSource.dataSourceKey.get();
    }

    public static void setDataSource(String dataSource) {
        DynamicDataSource.dataSourceKey.set(dataSource);
        DynamicDataSource dynamicDataSource = (DynamicDataSource) SpringUtils.getBean("dataSource");
        dynamicDataSource.afterPropertiesSet();
    }

    public static String getDataSource() {
        return DynamicDataSource.dataSourceKey.get();
    }

    public static void clear() {
        DynamicDataSource.dataSourceKey.remove();
    }

    public static void setDynamicDataSource(MysqlComponent mysqlComponent, String dataBase) {
        DruidDataSource druidDataSource = new DruidDataSource();
        String dynamicUrl = mysqlComponent.getUrl().replace("share", dataBase);
        druidDataSource.setUrl(dynamicUrl);
        druidDataSource.setUsername(mysqlComponent.getUsername());
        druidDataSource.setPassword(mysqlComponent.getPassword());
        druidDataSource.setDriverClassName(mysqlComponent.getDriverClass());
        DynamicDataSource.dataSourcesMap.put(dataBase, druidDataSource);
        DynamicDataSource.setDataSource(dataBase);
    }
}
