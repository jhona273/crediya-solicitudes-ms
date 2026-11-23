package co.com.crediya.model.solicitud;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EstadoSolicitud {
    PENDIENTE_REVISION("Pendiente de revisión"),
    REVISION_MANUAL("Revisión manual"),
    APROBADA("Aprobada"),
    RECHAZADA("Rechazada");

    private final String descripcion;
}
