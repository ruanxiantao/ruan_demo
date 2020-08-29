//package com.beijin.ruangateway.config;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//
///**
// * Create on 2019-07-25 01:55
// *
// * @author rxt
// */
//@Configuration
//public class GatewayRouteLocatorer {
//
//
//    @Bean
//    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//        RouteLocatorBuilder.Builder routes = builder.routes();
//        RouteLocatorBuilder.Builder serviceProvider = routes
//                .route("facadeRequestWithJson",
//                        r -> r
//                                .readBody(Object.class, readBody -> {
//                                    return true;
//                                })
//                                .and()
//                                .path("/hello")
//                                .uri("http://localhost:8090"));
//        RouteLocator routeLocator = serviceProvider.build();
//        return routeLocator;
//    }
//
//}
