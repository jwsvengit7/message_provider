package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteValidator {

        @Bean
        public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
            return routeLocatorBuilder.routes()
                    .route(p-> p.path("/sms/profile/**")
                            .filters(f->f.rewritePath("/sms/profile/(?<segment>.*)","/${segment}"))
                            .uri("lb://PROFILE-MODULE")
                    )
                    .route(p-> p.path("/sms/auth/**")
                            .filters(f->f.rewritePath("/sms/auth/(?<segment>.*)","/${segment}"))
                            .uri("lb://AUTH-MODULE")
                    )  .route(p-> p.path("/sms/payment/**")
                            .filters(f->f.rewritePath("/sms/payment/(?<segment>.*)","/${segment}"))
                            .uri("lb://PAYMENT-MODULE")
                    )

                    .build();
        }
    }

