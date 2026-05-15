package com.ecommerce.api.config;

import com.ecommerce.api.entity.Product;
import com.ecommerce.api.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CAPA: config
 *
 * Carga datos de ejemplo en la base de datos H2 al arrancar la aplicación.
 *
 * CommandLineRunner → interfaz funcional de Spring Boot.
 *   Spring llama a su método run() una vez que el contexto de aplicación
 *   está completamente inicializado (justo antes de que la app quede "lista").
 *
 * Esto permite tener datos para probar los endpoints desde el primer momento
 * sin necesidad de hacer manualmente POST para crear productos.
 *
 * Como H2 es en memoria (mem:), los datos desaparecen al detener la app.
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadInitialData(ProductRepository repository) {
        return args -> {
            // Solo cargar datos si la tabla está vacía (evita duplicados al reiniciar)
            if (repository.count() == 0) {
                repository.save(new Product(
                        "Laptop Gamer Pro",
                        "Intel Core i9, 32GB RAM, RTX 4080, SSD 1TB",
                        4500.0, 15, "Tecnología"
                ));
                repository.save(new Product(
                        "Mouse Inalámbrico RGB",
                        "DPI ajustable hasta 16000, batería 70 horas",
                        89.99, 120, "Periféricos"
                ));
                repository.save(new Product(
                        "Teclado Mecánico TKL",
                        "Switches Cherry MX Red, retroiluminación RGB por tecla",
                        159.99, 45, "Periféricos"
                ));
                repository.save(new Product(
                        "Monitor 4K 27 pulgadas",
                        "Panel IPS, 144Hz, HDR400, tiempo de respuesta 1ms",
                        799.99, 8, "Monitores"
                ));
                repository.save(new Product(
                        "Auriculares Gaming 7.1",
                        "Sonido surround virtual 7.1, micrófono con cancelación de ruido",
                        129.99, 60, "Audio"
                ));
                repository.save(new Product(
                        "SSD NVMe 1TB",
                        "Velocidad lectura 7000 MB/s, escritura 6500 MB/s, PCIe 4.0",
                        119.99, 200, "Almacenamiento"
                ));
                repository.save(new Product(
                        "Silla Gamer Ergonómica",
                        "Reposabrazos 4D, lumbar ajustable, reclinable 180°",
                        349.99, 25, "Mobiliario"
                ));

                System.out.println("✅ Datos de ejemplo cargados correctamente (7 productos)");
            }
        };
    }
}
