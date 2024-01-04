package com.example.apigateway.config;


import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


import java.util.*;
import java.util.function.Predicate;

@Component
public class APIGatewayRouteValidate {

    public static final List<String> openEndpoint = List.of(
            "/api/v1/auth/verify-otp",
            "/api/v1/auth/create",
            "/api/v1/auth/login",
            "/api/v1/auth/resend-otp",
            "/eureka"
    );
    public Predicate<ServerHttpRequest> isSecured() {
        return  serverRequest ->
                openEndpoint
                        .stream()
                        .noneMatch(url -> serverRequest.getURI().getPath().startsWith(url));
    }
}