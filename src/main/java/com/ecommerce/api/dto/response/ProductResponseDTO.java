package com.ecommerce.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "ProductResponse", description = "Datos del producto retornados por la API")
public class ProductResponseDTO {

    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Laptop Gamer")
    private String name;

    @Schema(description = "Descripción del producto", example = "RTX 4060, 16GB RAM, SSD 512GB")
    private String description;

    @Schema(description = "Precio del producto", example = "4500.0")
    private Double price;

    @Schema(description = "Cantidad en inventario", example = "10")
    private Integer stock;

    @Schema(description = "Categoría del producto", example = "Tecnologia")
    private String category;

    @Schema(description = "Fecha y hora de creación", example = "2024-05-15T14:30:00")
    private LocalDateTime createdAt;

    // Constructores
    public ProductResponseDTO() {}

    public ProductResponseDTO(Long id, String name, String description, Double price, Integer stock, String category, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
