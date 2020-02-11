package com.glqdlt.session;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jhun
 * 2020-02-11
 */
@ConfigurationProperties("session.jdbc.adapter")
public class JdbcSessionAdapterProperties {
    private String url;
    private String driverClassName;
    private String user;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
