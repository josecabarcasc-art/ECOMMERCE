package com.ecommerce.api.exception;

/**
 * CAPA: exception
 *
 * Excepción personalizada que se lanza cuando un producto no existe en la BD.
 *
 * Extiende RuntimeException para que sea una "unchecked exception":
 *   - No obliga a declarar throws en la firma del método.
 *   - Spring puede interceptarla con @ControllerAdvice sin problema.
 *
 * Se lanzará desde la capa Service cuando findById() no encuentre el producto.
 */
public class ProductNotFoundException extends RuntimeException {

    private final Long productId;

    /**
     * Constructor principal.
     *
     * @param id ID del producto que no fue encontrado
     */
    public ProductNotFoundException(Long id) {
        // Mensaje descriptivo que se incluirá en la respuesta JSON de error
        super("Producto con ID " + id + " no encontrado");
        this.productId = id;
    }

    /**
     * Permite recuperar el ID que causó el error (útil para logging).
     */
    public Long getProductId() {
        return productId;
    }
}
