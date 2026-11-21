package co.com.crediya.model.solicitud;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public final class SolicitudValidator {

    private static final Pattern FORMATO_DOCUMENTO = Pattern.compile("^\\d{5,15}$");

    private SolicitudValidator() {
    }

    public static void validar(NuevaSolicitud datos) {
        validarDocumento(datos.documentoIdentidad());
        validarMonto(datos.monto());
        validarPlazo(datos.plazo());
        validarTipoPrestamo(datos.tipoPrestamo());
    }

    private static void validarDocumento(String documento) {
        if (documento == null || !FORMATO_DOCUMENTO.matcher(documento).matches()) {
            throw new SolicitudInvalidaException("El documento de identidad debe tener entre 5 y 15 dígitos");
        }
    }

    private static void validarMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new SolicitudInvalidaException("El monto debe ser mayor a cero");
        }
    }

    private static void validarPlazo(Integer plazo) {
        if (plazo == null || plazo <= 0) {
            throw new SolicitudInvalidaException("El plazo debe ser mayor a cero (en meses)");
        }
    }

    private static void validarTipoPrestamo(String tipoPrestamo) {
        if (tipoPrestamo == null || tipoPrestamo.isBlank()) {
            throw new SolicitudInvalidaException("El tipo de préstamo es obligatorio");
        }
    }
}
