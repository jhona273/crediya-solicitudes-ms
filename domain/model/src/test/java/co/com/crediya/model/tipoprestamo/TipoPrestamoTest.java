package co.com.crediya.model.tipoprestamo;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class TipoPrestamoTest {

    private final TipoPrestamo tipo = TipoPrestamo.builder()
            .nombre("LIBRE_INVERSION")
            .montoMinimo(new BigDecimal("1000000"))
            .montoMaximo(new BigDecimal("50000000"))
            .build();

    @Test
    void admiteMonto_enElLimiteMinimo_devuelveTrue() {
        assertThat(tipo.admiteMonto(new BigDecimal("1000000"))).isTrue();
    }

    @Test
    void admiteMonto_enElLimiteMaximo_devuelveTrue() {
        assertThat(tipo.admiteMonto(new BigDecimal("50000000"))).isTrue();
    }

    @Test
    void admiteMonto_dentroDelRango_devuelveTrue() {
        assertThat(tipo.admiteMonto(new BigDecimal("5000000"))).isTrue();
    }

    @Test
    void admiteMonto_pordebajoDelMinimo_devuelveFalse() {
        assertThat(tipo.admiteMonto(new BigDecimal("999999"))).isFalse();
    }

    @Test
    void admiteMonto_porEncimaDelMaximo_devuelveFalse() {
        assertThat(tipo.admiteMonto(new BigDecimal("50000001"))).isFalse();
    }
}
