package com.ecommerce.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CAPA: config
 *
 * Configuración de Swagger / OpenAPI 3.
 *
 * @Configuration → indica que esta clase define beans de Spring
 *                  (equivale a un archivo XML de configuración).
 *
 * @Bean → el método retorna un objeto que Spring registra en el
 *          contexto de aplicación como un bean gestionado.
 *
 * springdoc-openapi lee este bean al arrancar y genera la
 * especificación OpenAPI con la información de la API.
 *
 * La UI queda disponible en: http://localhost:8080/swagger-ui.html
 * El JSON de la spec en:     http://localhost:8080/api-docs
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-commerce Product API")
                        .description(
                                "API RESTful para la gestión del catálogo de productos de un e-commerce. " +
                                "Desarrollada con Spring Boot + H2 Database para el Taller de Programación Backend."
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Programación Backend")
                                .email("backend@ecommerce.com")
                        )
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")
                        )
                );
    }
}
