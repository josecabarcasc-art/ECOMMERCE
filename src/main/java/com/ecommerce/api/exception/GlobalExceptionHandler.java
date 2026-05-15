package com.ecommerce.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CAPA: exception
 *
 * Manejador global de excepciones para toda la API.
 *
 * @RestControllerAdvice combina @ControllerAdvice + @ResponseBody:
 *   - @ControllerAdvice → intercepta excepciones lanzadas en CUALQUIER
 *                          @RestController de la aplicación.
 *   - @ResponseBody     → serializa automáticamente el objeto de retorno
 *                          a JSON en el cuerpo de la respuesta HTTP.
 *
 * Sin este handler, Spring devolvería una página HTML de error o el
 * stack trace completo. Con él, siempre respondemos con nuestro
 * ErrorResponse estructurado.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ----------------------------------------------------------------
    //  1. ProductNotFoundException → HTTP 404 Not Found
    // ----------------------------------------------------------------

    /**
     * Se activa cuando el Service lanza ProductNotFoundException.
     * Responde con 404 y un mensaje claro sobre el ID inexistente.
     *
     * @ExceptionHandler(ProductNotFoundException.class) → vincula este
     * método con el tipo de excepción que debe capturar.
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex) {

        ErrorResponse body = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),   // 404
                "Not Found",
                ex.getMessage(),                // "Producto con ID X no encontrado"
                null                            // sin lista de errores de campo
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // ----------------------------------------------------------------
    //  2. MethodArgumentNotValidException → HTTP 400 Bad Request
    // ----------------------------------------------------------------

    /**
     * Se activa cuando @Valid falla en el Controller (validaciones de Bean Validation).
     * Recopila TODOS los errores de campo y los devuelve en una lista.
     *
     * Ejemplo de respuesta:
     * {
     *   "status": 400,
     *   "error": "Bad Request",
     *   "message": "Error de validación en los datos enviados",
     *   "errors": [
     *     "name: El nombre del producto es obligatorio",
     *     "price: El precio debe ser un valor positivo"
     *   ]
     * }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {

        // Extraemos todos los errores de campo con formato "campo: mensaje"
        List<String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map((FieldError fe) -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse body = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),  // 400
                "Bad Request",
                "Error de validación en los datos enviados",
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // ----------------------------------------------------------------
    //  3. Exception genérica → HTTP 500 Internal Server Error
    // ----------------------------------------------------------------

    /**
     * Captura cualquier excepción no manejada arriba.
     * Evita que Spring devuelva stack traces al cliente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        ErrorResponse body = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "Internal Server Error",
                "Ocurrió un error interno. Por favor intente más tarde.",
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
