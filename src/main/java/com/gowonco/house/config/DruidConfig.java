package com.gowonco.house.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * druid 连接池
 * @author gowonco
 * @date 2019-07-16 22:49
 */
@Configuration
public class DruidConfig {

    /**
     * 连接池
     * @return
     */
    @ConfigurationProperties(prefix = "spring.druid")
    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        //设置过滤器
        dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
        return  dataSource;
    }

    /**
     * 慢SQL过滤器
     * @return
     */
    @Bean
    public StatFilter statFilter(){
        StatFilter filter = new StatFilter();
        // 慢日志时间
        filter.setSlowSqlMillis(5000);
        // 是否打印
        filter.setLogSlowSql(true);
        // 是否合并日志
        filter.setMergeSql(true);
        return filter;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return  new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
    }
}
