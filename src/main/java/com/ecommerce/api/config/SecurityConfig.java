package com.ecommerce.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * CAPA: config
 *
 * Configuración de seguridad web.
 *
 * IMPORTANTE: Spring Boot 3 incluye Spring Security en el classpath
 * automáticamente cuando está presente. Sin esta configuración,
 * la consola H2 y Swagger quedarían bloqueados por el filtro de seguridad
 * por defecto (que requiere login para todo).
 *
 * Esta clase configura reglas para:
 *   1. Permitir acceso público a los endpoints de la API (/products/**)
 *   2. Permitir acceso a la consola H2 (/h2-console/**)
 *   3. Permitir acceso a la documentación Swagger (/swagger-ui/**, /api-docs/**)
 *   4. Desactivar CSRF para peticiones a H2 Console (que usa frames)
 *   5. Permitir frames (necesario para renderizar la consola H2 en el navegador)
 *
 * NOTA: Esta configuración es apropiada para DESARROLLO.
 *       En producción se deben aplicar restricciones más estrictas.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitar CSRF globalmente (H2 Console lo requiere; en prod usar con cuidado)
            .csrf(csrf -> csrf.disable())

            // Permitir que la consola H2 muestre su UI en iframes
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )

            // Reglas de autorización por ruta
            .authorizeHttpRequests(auth -> auth
                // Consola H2
                .requestMatchers("/h2-console/**").permitAll()
                // Documentación Swagger / OpenAPI
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/api-docs/**",
                    "/v3/api-docs/**"
                ).permitAll()
                // Todos los endpoints de productos
                .requestMatchers("/products/**").permitAll()
                // Cualquier otra ruta también pública (ajustar según necesidades)
                .anyRequest().permitAll()
            );

        return http.build();
    }
}
