package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//
@Configuration
public class ConfigBeans {

  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }
////
//        @Bean
//        public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
//            return routeLocatorBuilder.routes()
//                    .route(path-> path.path("/sms/**")
//                            .filters(filterSpec->filterSpec.rewritePath("/sms/(?<segment>.*)","/${segment}"))
//                            .uri("lb://PROFILE-MODULE"))
//                    .route(path-> path.path("/sms/**")
//                            .filters(filterSpec->filterSpec.rewritePath("/sms/(?<segment>.*)","/${segment}"))
//                            .uri("lb://AUTH-MODULE"))
//                    .route(path-> path.path("/sms/**")
//                            .filters(filterSpec->filterSpec.rewritePath("/sms/(?<segment>.*)","/${segment}"))
//                            .uri("lb://PAYMENT-MODULE"))
//                    .build();
//        }

  }
//
