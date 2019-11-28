package com.beijing.ruan;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;

@SpringBootApplication
@ImportResource("classpath*:spring/spring-*.xml")
public class ProviderApplicationMain {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ProviderApplicationMain.class);
        springApplication.run(args);

    }

    @Bean
    public DruidDataSource getDruidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://192.168.110.128:3306/gmall?characterEncoding=UTF-8");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactoryBean getSqlSessionFactory() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(getDruidDataSource());
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean;

    }
//    @Bean
//    public InternalResourceViewResolver getInternalResourceViewResolver(){
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/html/");
//        viewResolver.setSuffix(".html");
//        return viewResolver;
//    }
//    @Bean
//    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter(){
//        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
//
//        return messageConverter;
//    }

//    @Bean
//    public MultipartResolver multipartResolver(){
//        MultipartResolver multipartResolver = new CommonsMultipartResolver();
//        return multipartResolver;
//    }
}
