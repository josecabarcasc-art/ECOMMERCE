package com.ecommerce.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot.
 *
 * @SpringBootApplication combina tres anotaciones:
 *   - @Configuration       → esta clase puede definir beans
 *   - @EnableAutoConfiguration → Spring Boot configura automáticamente
 *                               los componentes según las dependencias presentes
 *   - @ComponentScan       → escanea todos los paquetes hijos en busca de
 *                           componentes (@Controller, @Service, @Repository, etc.)
 */
@SpringBootApplication
public class EcommerceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApiApplication.class, args);
    }
}
