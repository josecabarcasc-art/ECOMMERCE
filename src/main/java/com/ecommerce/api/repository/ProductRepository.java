package com.ecommerce.api.repository;

import com.ecommerce.api.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CAPA: repository
 *
 * Interfaz que extiende JpaRepository para operaciones CRUD sobre Product.
 *
 * JpaRepository<Product, Long> provee automáticamente (sin código extra):
 *   - save(entity)            → INSERT o UPDATE
 *   - findById(id)            → SELECT por PK → devuelve Optional<Product>
 *   - findAll()               → SELECT *
 *   - findAll(Pageable)       → SELECT * con paginación y ordenamiento
 *   - deleteById(id)          → DELETE por PK
 *   - existsById(id)          → comprueba existencia
 *   - count()                 → total de registros
 *   ...entre otros.
 *
 * Spring Data JPA genera la implementación en tiempo de ejecución;
 * nosotros solo declaramos la interfaz.
 *
 * @Repository → marca este bean como componente de acceso a datos.
 *               Permite que Spring lo detecte en el escaneo de componentes.
 *               También traduce excepciones de JPA a excepciones de Spring.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Método de paginación estándar heredado de JpaRepository.
     * Spring Data lo implementa automáticamente.
     *
     * @param pageable objeto que encapsula: número de página, tamaño y ordenamiento
     * @return         página de productos (datos + metadatos de paginación)
     */
    Page<Product> findAll(Pageable pageable);

    // ----------------------------------------------------------------
    //  Métodos opcionales para puntos extra
    //  Spring Data JPA los implementa interpretando el nombre del método.
    // ----------------------------------------------------------------

    /**
     * Busca productos cuya categoría coincida exactamente (case-insensitive).
     * Equivale a: SELECT * FROM products WHERE LOWER(category) = LOWER(:category)
     */
    Page<Product> findByCategoryIgnoreCase(String category, Pageable pageable);

    /**
     * Busca productos cuyo nombre contenga la cadena dada (case-insensitive).
     * Equivale a: SELECT * FROM products WHERE LOWER(name) LIKE %:name%
     */
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
