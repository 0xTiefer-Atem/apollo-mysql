package org.springboot.apollo.demo.entity;

import lombok.Data;

/**
 * @author： Wang Qian
 * @date： 2020/7/29 上午 9:19
 */
@Data
public class DataSourceModel {
    private String username;
    private String password;
    private String url;
    private String driverClassName;

    public DataSourceModel(String username, String password, String url, String driverClassName) {
        this.username = username;
        this.password = password;
        this.url = url;
        this.driverClassName = driverClassName;
    }
}
