package co.com.crediya.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RegistrarSolicitudRequest(
        @NotBlank(message = "El documento de identidad es obligatorio") String documentoIdentidad,
        @NotNull(message = "El monto es obligatorio")
        @Positive(message = "El monto debe ser mayor a cero") BigDecimal monto,
        @NotNull(message = "El plazo es obligatorio")
        @Positive(message = "El plazo debe ser mayor a cero (en meses)") Integer plazo,
        @NotBlank(message = "El tipo de préstamo es obligatorio") String tipoPrestamo) {
}
