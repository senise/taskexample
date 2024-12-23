package com.senise.taskexample.infrastructure.configuration.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para Swagger/OpenAPI.
 */
@Configuration
public class SwaggerConfig {

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer_Authentication"))  // Aplica el requisito de Bearer Authentication
                .components(new Components().addSecuritySchemes(
                        "Bearer_Authentication", createAPIKeyScheme()))  // Define el esquema de Bearer
                .info(new Info()
                        .title("TASK EXAMPLE API REST")
                        .description("EJEMPLO DE API REST CON SPRINGBOOT ")
                        .version("1.0")
                        .contact(new Contact().name("SENISE")));
    }
}