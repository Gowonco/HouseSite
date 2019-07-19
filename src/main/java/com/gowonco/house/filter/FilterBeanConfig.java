package com.gowonco.house.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gowonco
 * @date 2019-07-09 0:59
 */

@Configuration
public class FilterBeanConfig {


    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new LogFilter());
        List<String> urList = new ArrayList<>();
        urList.add("*");
        filterRegistrationBean.setUrlPatterns(urList);
        return filterRegistrationBean;
    }
}
