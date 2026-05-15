package com.ecommerce.api.mapper;

import com.ecommerce.api.dto.request.ProductRequestDTO;
import com.ecommerce.api.dto.response.ProductResponseDTO;
import com.ecommerce.api.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    /**
     * Convierte de DTO de Request a Entidad
     */
    public Product toEntity(ProductRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(dto.getCategory());
        return product;
    }

    /**
     * Convierte de Entidad a DTO de Response
     */
    public ProductResponseDTO toResponseDTO(Product entity) {
        if (entity == null) {
            return null;
        }
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        dto.setCategory(entity.getCategory());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    /**
     * Actualiza una entidad existente con los datos del DTO
     */
    public void updateEntityFromDTO(ProductRequestDTO dto, Product entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock());
        entity.setCategory(dto.getCategory());
    }
}
