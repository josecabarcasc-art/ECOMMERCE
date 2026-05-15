package com.ecommerce.api.controller;

import com.ecommerce.api.dto.request.ProductRequestDTO;
import com.ecommerce.api.dto.response.ProductResponseDTO;
import com.ecommerce.api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST que expone los endpoints CRUD del catálogo de productos.
 *
 * Flujo: Request HTTP → Controller → Service → Repository → BD H2
 */
@RestController
@RequestMapping("/products")
@Tag(name = "Productos", description = "Endpoints CRUD para administrar el catálogo de productos del e-commerce")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ================================================================
    //  GET /products — Listar todos los productos con paginación
    // ================================================================

    @Operation(
        summary = "Listar todos los productos",
        description = "Retorna una lista paginada de productos. Se puede controlar la paginación " +
                      "con los parámetros `page` y `size`, y el ordenamiento con `sortBy` y `direction`.\n\n" +
                      "**Ejemplo:** `/products?page=0&size=5&sortBy=price&direction=asc`"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    })
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @Parameter(description = "Número de página (inicia en 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Cantidad de productos por página", example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Campo por el cual ordenar (id, name, price, stock, category)", example = "id")
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(description = "Dirección del orden: asc (ascendente) o desc (descendente)", example = "asc")
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    // ================================================================
    //  GET /products/{id} — Obtener producto por ID
    // ================================================================

    @Operation(
        summary = "Obtener producto por ID",
        description = "Busca y retorna un producto específico utilizando su ID. " +
                      "Si el producto no existe, retorna un error 404 con un mensaje descriptivo."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente",
                     content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado — el ID proporcionado no existe en la base de datos",
                     content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @Parameter(description = "ID único del producto", required = true, example = "1")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // ================================================================
    //  POST /products — Crear un nuevo producto
    // ================================================================

    @Operation(
        summary = "Crear un nuevo producto",
        description = "Crea un producto con los datos proporcionados en el cuerpo de la petición. " +
                      "Los campos `name`, `price`, `stock` y `category` son obligatorios. " +
                      "El campo `createdAt` se asigna automáticamente.\n\n" +
                      "**Retorna:** HTTP 201 Created con el producto creado."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                     content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Error de validación — datos de entrada inválidos (campos vacíos, precio negativo, etc.)",
                     content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del nuevo producto",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = ProductRequestDTO.class),
                    examples = @ExampleObject(
                        name = "Ejemplo de producto",
                        value = """
                            {
                                "name": "Laptop Gamer",
                                "description": "RTX 4060, 16GB RAM, SSD 512GB",
                                "price": 4500.0,
                                "stock": 10,
                                "category": "Tecnologia"
                            }
                            """
                    )
                )
            )
            @Valid @RequestBody ProductRequestDTO productRequestDTO
    ) {
        ProductResponseDTO created = productService.createProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ================================================================
    //  PUT /products/{id} — Actualizar un producto existente
    // ================================================================

    @Operation(
        summary = "Actualizar un producto existente",
        description = "Actualiza todos los campos del producto identificado por el ID proporcionado. " +
                      "Si el producto no existe, retorna 404. Los datos enviados deben pasar las mismas " +
                      "validaciones que al crear un producto."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
                     content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Error de validación — datos de entrada inválidos",
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado — el ID proporcionado no existe",
                     content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @Parameter(description = "ID del producto a actualizar", required = true, example = "1")
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos del producto",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = ProductRequestDTO.class),
                    examples = @ExampleObject(
                        name = "Ejemplo de actualización",
                        value = """
                            {
                                "name": "Laptop Gamer Pro Max",
                                "description": "RTX 4080, 32GB RAM, SSD 1TB",
                                "price": 5999.99,
                                "stock": 5,
                                "category": "Tecnologia"
                            }
                            """
                    )
                )
            )
            @Valid @RequestBody ProductRequestDTO productRequestDTO
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequestDTO));
    }

    // ================================================================
    //  DELETE /products/{id} — Eliminar un producto
    // ================================================================

    @Operation(
        summary = "Eliminar un producto",
        description = "Elimina permanentemente el producto identificado por el ID. " +
                      "Retorna HTTP 204 (No Content) si la eliminación fue exitosa. " +
                      "Si el producto no existe, retorna 404."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado — el ID proporcionado no existe",
                     content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID del producto a eliminar", required = true, example = "1")
            @PathVariable Long id
    ) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // ================================================================
    //  GET /products/search/category — [Extra] Buscar por categoría
    // ================================================================

    @Operation(
        summary = "[Extra] Buscar por categoría",
        description = "Filtra productos cuya categoría coincida exactamente con el valor proporcionado " +
                      "(no distingue mayúsculas/minúsculas). Soporta paginación.\n\n" +
                      "**Ejemplo:** `/products/search/category?category=Tecnologia&page=0&size=5`"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resultados de búsqueda obtenidos")
    })
    @GetMapping("/search/category")
    public ResponseEntity<Page<ProductResponseDTO>> getByCategory(
            @Parameter(description = "Nombre de la categoría a buscar", required = true, example = "Tecnologia")
            @RequestParam String category,

            @Parameter(description = "Número de página", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Elementos por página", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.getProductsByCategory(category, pageable));
    }

    // ================================================================
    //  GET /products/search/name — [Extra] Buscar por nombre
    // ================================================================

    @Operation(
        summary = "[Extra] Buscar por nombre",
        description = "Busca productos cuyo nombre contenga el texto proporcionado " +
                      "(no distingue mayúsculas/minúsculas). Soporta paginación.\n\n" +
                      "**Ejemplo:** `/products/search/name?name=laptop&page=0&size=5`"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resultados de búsqueda obtenidos")
    })
    @GetMapping("/search/name")
    public ResponseEntity<Page<ProductResponseDTO>> searchByName(
            @Parameter(description = "Texto a buscar en el nombre del producto", required = true, example = "Laptop")
            @RequestParam String name,

            @Parameter(description = "Número de página", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Elementos por página", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.searchProductsByName(name, pageable));
    }
}
