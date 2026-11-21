package co.com.crediya.api;

import co.com.crediya.model.solicitud.NuevaSolicitud;
import co.com.crediya.model.solicitud.Solicitud;

public final class SolicitudApiMapper {

    private SolicitudApiMapper() {
    }

    public static NuevaSolicitud toDomain(RegistrarSolicitudRequest request) {
        return new NuevaSolicitud(
                request.documentoIdentidad(),
                request.monto(),
                request.plazo(),
                request.tipoPrestamo());
    }

    public static SolicitudResponse toResponse(Solicitud solicitud) {
        return new SolicitudResponse(
                solicitud.getId(),
                solicitud.getDocumentoIdentidad(),
                solicitud.getMonto(),
                solicitud.getPlazo(),
                solicitud.getTipoPrestamo().getNombre(),
                solicitud.getEstado().getDescripcion());
    }
}
