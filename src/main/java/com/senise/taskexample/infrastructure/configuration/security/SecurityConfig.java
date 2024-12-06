package com.senise.taskexample.infrastructure.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))  // Deshabilitar frameOptions
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**")
                        /*.requestMatchers(
                                "/swagger-ui/**",           // Swagger UI
                                "/swagger-ui.html",         // Swagger UI HTML
                                "/v3/api-docs/**",          // Documentación de Swagger
                                "/swagger-resources/**",    // Recursos de Swagger
                                "/webjars/**",              // Swagger webjars (js, css)
                                "/api-docs/swagger-config", // Configuración de Swagger
                                "/api-docs/**",             // API Docs Swagger
                                "/h2-console/**",           // Acceso H2 console
                                "/api/v1/auth/**"           // Registro y login usuario
                        )*/.permitAll()  // Permitir acceso sin autenticación a estos endpoints
                        // Configurar las rutas con permisos para los roles USER y ADMIN
                        //.requestMatchers("/api/v1/tasks/**").hasRole(Role.USER.name()) // Usuarios pueden acceder a sus tareas
                        //.requestMatchers("/api/v1/admin/**").hasRole(Role.ADMIN.name()) // Solo admin puede gestionar usuarios y tareas
                        .anyRequest().authenticated()  // Las demás peticiones requieren autenticación
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Configuración para no usar sesión
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);  // Agregar filtro de autenticación JWT
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/swagger-ui/**", "/v3/api-docs/**" // Rutas de Swagger que no necesitan autenticación
        );
    }
}

