package com.example.apigateway.filter;

import com.example.apigateway.config.APIGatewayRouteValidate;
import com.example.apigateway.exceptions.CustomNotFoundException;
import com.example.apigateway.helpers.headers.Headers;
import com.example.apigateway.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import static com.example.apigateway.helpers.keys.Keys.*;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private APIGatewayRouteValidate validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured().test(exchange.getRequest())) {
                logger.info("Incoming request to secured endpoint: {}", exchange.getRequest().getURI());
                HttpHeaders headers = exchange.getRequest().getHeaders();
                if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                    logger.warn("Missing authorization header");
                    throw new CustomNotFoundException("Missing authorization header");
                }

                String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtUtil.validateToken(authHeader);
                } catch (Exception e) {
                    logger.warn("Invalid access...!");
                    throw new CustomNotFoundException("Unauthorized access to application");
                }


                exchange.getRequest().mutate()
                        .header(Headers.APPID.name(), APPID)
                        .header(Headers.APP_KEY.name(), KEYS)
                        .header(Headers.APP_SECRET.name(), APP_SECRET)
                        .build();
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}





