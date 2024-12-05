package com.senise.taskexample.infrastructure.configuration.CorsConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configurar CORS para todas las rutas
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")  // Reemplaza con tu frontend si es necesario
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")  // Métodos permitidos
                .allowCredentials(true)  // Permitir enviar cookies o credenciales si es necesario
                .maxAge(3600);  // Duración del caché de la configuración CORS
    }
}
