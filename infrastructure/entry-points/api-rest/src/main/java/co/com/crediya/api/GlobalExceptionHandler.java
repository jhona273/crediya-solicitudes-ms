package co.com.crediya.api;

import co.com.crediya.model.solicitud.SolicitudInvalidaException;
import co.com.crediya.model.solicitud.TipoPrestamoNoExisteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String CLAVE_MENSAJE = "mensaje";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(MethodArgumentNotValidException e) {
        Map<String, String> errores = new HashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(f -> errores.put(f.getField(), f.getDefaultMessage()));
        return ResponseEntity.badRequest()
                .body(Map.of(CLAVE_MENSAJE, "Datos inválidos", "errores", errores));
    }

    @ExceptionHandler(SolicitudInvalidaException.class)
    public ResponseEntity<Map<String, String>> manejarSolicitudInvalida(SolicitudInvalidaException e) {
        log.warn("Solicitud inválida: {}", e.getMessage());
        return ResponseEntity.badRequest().body(Map.of(CLAVE_MENSAJE, e.getMessage()));
    }

    @ExceptionHandler(TipoPrestamoNoExisteException.class)
    public ResponseEntity<Map<String, String>> manejarTipoInexistente(TipoPrestamoNoExisteException e) {
        log.warn("Tipo de préstamo inexistente: {}", e.getMessage());
        return ResponseEntity.badRequest().body(Map.of(CLAVE_MENSAJE, e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarJsonInvalido(HttpMessageNotReadableException e) {
        log.warn("JSON inválido: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(Map.of(CLAVE_MENSAJE, "El cuerpo de la petición es inválido o tiene datos con formato incorrecto"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarGenerico(Exception e) {
        log.error("Error inesperado", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(CLAVE_MENSAJE, "Ocurrió un error inesperado"));
    }
}
