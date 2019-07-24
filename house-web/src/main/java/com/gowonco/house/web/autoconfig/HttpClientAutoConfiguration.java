package com.gowonco.house.web.autoconfig;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义起步依赖
 * @author gowonco
 * @date 2019-07-22 17:03
 */
@Configuration
@ConditionalOnClass({HttpClient.class})
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoConfiguration {
    private final HttpClientProperties properties;

    public HttpClientAutoConfiguration(HttpClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(HttpClient.class)
    public HttpClient httpClient() {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(properties.getConnectTimeOut())
                .setSocketTimeout(properties.getSocketTimeOut()).build();//构建requestConfig
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
                .setUserAgent(properties.getAgent())
                .setMaxConnPerRoute(properties.getMaxConnPreRoute())
                .setConnectionReuseStrategy(new NoConnectionReuseStrategy())
                .build();
        return client;
    }
}
