package com.senise.taskexample.infrastructure.configuration.http;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Filtro HTTP que intercepta todas las solicitudes y respuestas.
 */
@Component
@Slf4j
public class HttpRequestFilter implements Filter {

    /**
     * Método que realiza el filtrado de las solicitudes y respuestas HTTP.
     *
     * @param request  la solicitud HTTP entrante.
     * @param response la respuesta HTTP saliente.
     * @param chain    el filtro chain que permite continuar la petición.
     * @throws IOException      en caso de error de I/O.
     * @throws ServletException en caso de errores en el procesamiento del servlet.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.debug("Request: {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());
        chain.doFilter(request, response);
    }
}
