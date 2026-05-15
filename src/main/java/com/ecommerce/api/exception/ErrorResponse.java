package com.ecommerce.api.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CAPA: exception
 *
 * DTO (Data Transfer Object) que estructura las respuestas de error.
 *
 * En lugar de devolver el stack trace de Java (que expone detalles internos
 * y es ilegible para el cliente), esta clase produce un JSON claro como:
 *
 * {
 *   "timestamp": "2024-05-10T14:30:00",
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "Producto con ID 99 no encontrado",
 *   "errors": null
 * }
 */
public class ErrorResponse {

    /** Momento exacto en que ocurrió el error. */
    private LocalDateTime timestamp;

    /** Código HTTP numérico (404, 400, 500, etc.). */
    private int status;

    /** Nombre legible del error HTTP ("Not Found", "Bad Request", etc.). */
    private String error;

    /** Mensaje principal descriptivo del problema. */
    private String message;

    /**
     * Lista de errores de validación campo a campo.
     * Solo se rellena en errores 400 (Bad Request).
     * Ejemplo: ["El nombre del producto es obligatorio", "El precio debe ser positivo"]
     */
    private List<String> errors;

    // ----------------------------------------------------------------
    //  Constructor
    // ----------------------------------------------------------------

    public ErrorResponse(int status, String error, String message, List<String> errors) {
        this.timestamp = LocalDateTime.now();
        this.status    = status;
        this.error     = error;
        this.message   = message;
        this.errors    = errors;
    }

    // ----------------------------------------------------------------
    //  Getters (necesarios para que Jackson serialice a JSON)
    // ----------------------------------------------------------------

    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus()              { return status; }
    public String getError()            { return error; }
    public String getMessage()          { return message; }
    public List<String> getErrors()     { return errors; }
}
