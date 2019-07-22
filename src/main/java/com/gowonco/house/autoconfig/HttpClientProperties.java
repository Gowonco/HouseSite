package com.gowonco.house.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author gowonco
 * @date 2019-07-22 17:05
 */
@ConfigurationProperties(prefix = "spring.httpclient")
public class HttpClientProperties {
    // 连接超时时间
    private Integer connectTimeOut = 1000;
    // 主超时时间
    private Integer socketTimeOut = 10000;
    private String agent = "agent";
    // ip最大连接数
    private Integer maxConnPreRoute = 10;
    // 总的连接数
    private Integer maxConnTotaol = 50;

    public Integer getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(Integer connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public Integer getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setSocketTimeOut(Integer socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public Integer getMaxConnPreRoute() {
        return maxConnPreRoute;
    }

    public void setMaxConnPreRoute(Integer maxConnPreRoute) {
        this.maxConnPreRoute = maxConnPreRoute;
    }

    public Integer getMaxConnTotaol() {
        return maxConnTotaol;
    }

    public void setMaxConnTotaol(Integer maxConnTotaol) {
        this.maxConnTotaol = maxConnTotaol;
    }
}
