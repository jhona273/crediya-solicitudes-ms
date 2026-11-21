package co.com.crediya.jpa.tipoprestamo;

import co.com.crediya.model.tipoprestamo.TipoPrestamo;

public final class TipoPrestamoMapper {

    private TipoPrestamoMapper() {
    }

    public static TipoPrestamo toDomain(TipoPrestamoData data) {
        return TipoPrestamo.builder()
                .id(data.getId())
                .nombre(data.getNombre())
                .montoMinimo(data.getMontoMinimo())
                .montoMaximo(data.getMontoMaximo())
                .tasaInteres(data.getTasaInteres())
                .validacionAutomatica(data.isValidacionAutomatica())
                .build();
    }

    public static TipoPrestamoData toData(TipoPrestamo tipo) {
        return TipoPrestamoData.builder()
                .id(tipo.getId())
                .nombre(tipo.getNombre())
                .montoMinimo(tipo.getMontoMinimo())
                .montoMaximo(tipo.getMontoMaximo())
                .tasaInteres(tipo.getTasaInteres())
                .validacionAutomatica(tipo.isValidacionAutomatica())
                .build();
    }
}