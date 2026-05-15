package com.ecommerce.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(name = "ProductRequest", description = "Datos requeridos para crear o actualizar un producto")
public class ProductRequestDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Schema(description = "Nombre del producto", example = "Laptop Gamer", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Descripción detallada del producto", example = "RTX 4060, 16GB RAM, SSD 512GB")
    private String description;

    @Positive(message = "El precio debe ser un valor positivo")
    @Schema(description = "Precio del producto (debe ser mayor a 0)", example = "4500.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double price;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @Schema(description = "Cantidad en inventario (mínimo 0)", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer stock;

    @NotBlank(message = "La categoría es obligatoria")
    @Schema(description = "Categoría del producto", example = "Tecnologia", requiredMode = Schema.RequiredMode.REQUIRED)
    private String category;

    // Constructores
    public ProductRequestDTO() {}

    public ProductRequestDTO(String name, String description, Double price, Integer stock, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    // Getters y Setters
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
}
