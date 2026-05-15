package com.ecommerce.api.service;

import com.ecommerce.api.dto.request.ProductRequestDTO;
import com.ecommerce.api.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductResponseDTO> getAllProducts(Pageable pageable);

    ProductResponseDTO getProductById(Long id);

    ProductResponseDTO createProduct(ProductRequestDTO productDTO);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productDetails);

    void deleteProduct(Long id);

    Page<ProductResponseDTO> getProductsByCategory(String category, Pageable pageable);

    Page<ProductResponseDTO> searchProductsByName(String name, Pageable pageable);
}
