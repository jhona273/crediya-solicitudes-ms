package co.com.crediya.model.solicitud;

import java.math.BigDecimal;

public record NuevaSolicitud(String documentoIdentidad,
                             BigDecimal monto,
                             Integer plazo,
                             String tipoPrestamo) {
}
