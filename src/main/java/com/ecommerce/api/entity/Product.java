package com.ecommerce.api.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * CAPA: entity
 *
 * Representa la tabla PRODUCT en la base de datos H2.
 *
 * @Entity  → Le indica a JPA que esta clase es una entidad persistible.
 * @Table   → Especifica el nombre de la tabla en la BD (opcional; sin ella
 *             JPA usaría el nombre de la clase).
 *
 * Cada campo con una anotación de Jakarta Validation será verificado
 * ANTES de intentar guardar en la BD (se activa desde el Controller).
 */
@Entity
@Table(name = "products")
public class Product {

    /**
     * @Id             → Clave primaria de la entidad.
     * @GeneratedValue → JPA genera el valor automáticamente.
     *   IDENTITY      → delega la generación a la BD (autoincrement en H2).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private String category;

    /**
     * Se asigna automáticamente antes de persistir por primera vez.
     * @PrePersist → método de ciclo de vida JPA que se ejecuta justo
     *               antes del INSERT en la BD.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ----------------------------------------------------------------
    //  Constructores
    // ----------------------------------------------------------------

    public Product() {}

    public Product(String name, String description, Double price,
                   Integer stock, String category) {
        this.name        = name;
        this.description = description;
        this.price       = price;
        this.stock       = stock;
        this.category    = category;
    }

    // ----------------------------------------------------------------
    //  Getters y Setters (JPA los necesita para operar)
    // ----------------------------------------------------------------

    public Long getId()                    { return id; }
    public void setId(Long id)             { this.id = id; }

    public String getName()                { return name; }
    public void setName(String name)       { this.name = name; }

    public String getDescription()                     { return description; }
    public void setDescription(String description)     { this.description = description; }

    public Double getPrice()               { return price; }
    public void setPrice(Double price)     { this.price = price; }

    public Integer getStock()              { return stock; }
    public void setStock(Integer stock)    { this.stock = stock; }

    public String getCategory()                    { return category; }
    public void setCategory(String category)       { this.category = category; }

    public LocalDateTime getCreatedAt()                    { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt)      { this.createdAt = createdAt; }
}
