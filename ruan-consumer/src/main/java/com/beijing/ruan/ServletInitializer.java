package com.beijing.ruan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author ruanxiantao
 */
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        SpringApplicationBuilder builder = application.sources(ConsumerApplicationMain.class);
        SpringApplication springApplication = builder.application();
//        springApplication.addListeners();
        return builder;
    }

}
