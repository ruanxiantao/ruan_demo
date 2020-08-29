package com.beijin.ruangateway.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @Author: ruanxiantao
 * @Description:
 * @Date: 2020/8/1 15:38
 */
@Configuration
public class GatewayConfig {

//    @Bean
    public FilterRegistrationBean myfilter() {
        Filter myFilter = new MyFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(myFilter);
        registrationBean.addUrlPatterns("/**");
        registrationBean.setOrder(1);
        return registrationBean;
    }
//    @Bean
    public FilterRegistrationBean yourfilter() {
        YourFilter yourFilter = new YourFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(yourFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(2);
        return registrationBean;
    }

}
