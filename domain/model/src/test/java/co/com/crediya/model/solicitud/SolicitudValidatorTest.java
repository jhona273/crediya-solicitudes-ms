package co.com.crediya.model.solicitud;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SolicitudValidatorTest {

    private static final String DOCUMENTO_VALIDO = "1020304050";
    private static final BigDecimal MONTO_VALIDO = new BigDecimal("5000000");
    private static final Integer PLAZO_VALIDO = 24;
    private static final String TIPO_VALIDO = "LIBRE_INVERSION";

    @Test
    void validar_conDatosValidos_noLanzaExcepcion() {
        NuevaSolicitud datos = new NuevaSolicitud(DOCUMENTO_VALIDO, MONTO_VALIDO, PLAZO_VALIDO, TIPO_VALIDO);

        assertThatCode(() -> SolicitudValidator.validar(datos)).doesNotThrowAnyException();
    }

    @Test
    void validar_conDocumentoConLetras_lanzaExcepcion() {
        assertInvalida(new NuevaSolicitud("abc", MONTO_VALIDO, PLAZO_VALIDO, TIPO_VALIDO), "documento");
    }

    @Test
    void validar_conDocumentoMuyCorto_lanzaExcepcion() {
        assertInvalida(new NuevaSolicitud("1234", MONTO_VALIDO, PLAZO_VALIDO, TIPO_VALIDO), "documento");
    }

    @Test
    void validar_conDocumentoNulo_lanzaExcepcion() {
        assertInvalida(new NuevaSolicitud(null, MONTO_VALIDO, PLAZO_VALIDO, TIPO_VALIDO), "documento");
    }

    @Test
    void validar_conMontoCero_lanzaExcepcion() {
        assertInvalida(new NuevaSolicitud(DOCUMENTO_VALIDO, BigDecimal.ZERO, PLAZO_VALIDO, TIPO_VALIDO), "monto");
    }

    @Test
    void validar_conMontoNegativo_lanzaExcepcion() {
        assertInvalida(new NuevaSolicitud(DOCUMENTO_VALIDO, new BigDecimal("-5"), PLAZO_VALIDO, TIPO_VALIDO), "monto");
    }

    @Test
    void validar_conMontoNulo_lanzaExcepcion() {
        assertInvalida(new NuevaSolicitud(DOCUMENTO_VALIDO, null, PLAZO_VALIDO, TIPO_VALIDO), "monto");
    }

    @Test
    void validar_conPlazoCero_lanzaExcepcion() {
        assertInvalida(new NuevaSolicitud(DOCUMENTO_VALIDO, MONTO_VALIDO, 0, TIPO_VALIDO), "plazo");
    }

    @Test
    void validar_conPlazoNulo_lanzaExcepcion() {
        assertInvalida(new NuevaSolicitud(DOCUMENTO_VALIDO, MONTO_VALIDO, null, TIPO_VALIDO), "plazo");
    }

    @Test
    void validar_conTipoVacio_lanzaExcepcion() {
        assertInvalida(new NuevaSolicitud(DOCUMENTO_VALIDO, MONTO_VALIDO, PLAZO_VALIDO, "  "), "tipo de préstamo");
    }

    @Test
    void validar_conTipoNulo_lanzaExcepcion() {
        assertInvalida(new NuevaSolicitud(DOCUMENTO_VALIDO, MONTO_VALIDO, PLAZO_VALIDO, null), "tipo de préstamo");
    }

    private void assertInvalida(NuevaSolicitud datos, String fragmentoMensaje) {
        assertThatThrownBy(() -> SolicitudValidator.validar(datos))
                .isInstanceOf(SolicitudInvalidaException.class)
                .hasMessageContaining(fragmentoMensaje);
    }
}
