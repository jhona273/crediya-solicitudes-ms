package co.com.crediya.jpa.solicitud;

import co.com.crediya.jpa.tipoprestamo.TipoPrestamoMapper;
import co.com.crediya.model.solicitud.Solicitud;

public final class SolicitudMapper {

    private SolicitudMapper() {
    }

    public static SolicitudData toData(Solicitud solicitud) {
        return SolicitudData.builder()
                .id(solicitud.getId())
                .documentoIdentidad(solicitud.getDocumentoIdentidad())
                .monto(solicitud.getMonto())
                .plazo(solicitud.getPlazo())
                .tipoPrestamo(TipoPrestamoMapper.toData(solicitud.getTipoPrestamo()))
                .estado(solicitud.getEstado())
                .build();
    }

    public static Solicitud toDomain(SolicitudData data) {
        return Solicitud.builder()
                .id(data.getId())
                .documentoIdentidad(data.getDocumentoIdentidad())
                .monto(data.getMonto())
                .plazo(data.getPlazo())
                .tipoPrestamo(TipoPrestamoMapper.toDomain(data.getTipoPrestamo()))
                .estado(data.getEstado())
                .build();
    }
}