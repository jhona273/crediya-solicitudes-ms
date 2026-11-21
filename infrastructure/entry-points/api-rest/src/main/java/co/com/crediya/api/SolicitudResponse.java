package co.com.crediya.api;

import java.math.BigDecimal;

public record SolicitudResponse(Long id,
                                String documentoIdentidad,
                                BigDecimal monto,
                                Integer plazo,
                                String tipoPrestamo,
                                String estado) {
}
