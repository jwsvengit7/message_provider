package com.example.authmodule.web.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                contact = @Contact(name = "Jack William Chiorlu", email = "chiorlujack@gmail.com", url = "https://www.github.com/jwsvengit7"),
                description = "Open API documentation for Spring security.",
                title = "SMS READER",
                version = "1.0",
                license = @License(name = "Apache License", url = "https://www.apache.org/licenses/LICENSE-2"),
                termsOfService = "Terms of Service"
        ),
        servers = {
                @Server(
                        description = "DEV ENV",
                        url = "http://localhost:8082"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://cardmonix-production.up.railway.app"
                )
        },
        security = {
                @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "Bearer Authentication")
        }
)

@io.swagger.v3.oas.annotations.security.SecurityScheme(
        name = "Bearer Authentication",
        description = "JWT Authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiSwagger {


}