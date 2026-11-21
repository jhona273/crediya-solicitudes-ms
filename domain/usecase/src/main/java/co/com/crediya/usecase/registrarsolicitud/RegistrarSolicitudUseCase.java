package co.com.crediya.usecase.registrarsolicitud;

import co.com.crediya.model.solicitud.EstadoSolicitud;
import co.com.crediya.model.solicitud.NuevaSolicitud;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.SolicitudInvalidaException;
import co.com.crediya.model.solicitud.SolicitudValidator;
import co.com.crediya.model.solicitud.TipoPrestamoNoExisteException;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import co.com.crediya.model.tipoprestamo.TipoPrestamo;
import co.com.crediya.model.tipoprestamo.gateways.TipoPrestamoRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Locale;

@RequiredArgsConstructor
public class RegistrarSolicitudUseCase {

    private final SolicitudRepository solicitudRepository;
    private final TipoPrestamoRepository tipoPrestamoRepository;

    public Solicitud ejecutar(NuevaSolicitud datos) {
        if (datos == null) {
            throw new SolicitudInvalidaException("La solicitud es obligatoria");
        }
        NuevaSolicitud normalizada = normalizar(datos);
        SolicitudValidator.validar(normalizada);
        TipoPrestamo tipo = buscarTipoPrestamo(normalizada.tipoPrestamo());
        validarMontoEnRango(normalizada.monto(), tipo);
        return solicitudRepository.guardar(construirSolicitud(normalizada, tipo));
    }

    private NuevaSolicitud normalizar(NuevaSolicitud datos) {
        return new NuevaSolicitud(
                limpiar(datos.documentoIdentidad()),
                datos.monto(),
                datos.plazo(),
                limpiarTipo(datos.tipoPrestamo()));
    }

    private TipoPrestamo buscarTipoPrestamo(String nombre) {
        return tipoPrestamoRepository.buscarPorNombre(nombre)
                .orElseThrow(() -> new TipoPrestamoNoExisteException(nombre));
    }

    private void validarMontoEnRango(BigDecimal monto, TipoPrestamo tipo) {
        if (!tipo.admiteMonto(monto)) {
            throw new SolicitudInvalidaException(String.format(
                    "El monto para %s debe estar entre %s y %s",
                    tipo.getNombre(), tipo.getMontoMinimo(), tipo.getMontoMaximo()));
        }
    }

    private Solicitud construirSolicitud(NuevaSolicitud datos, TipoPrestamo tipo) {
        return Solicitud.builder()
                .documentoIdentidad(datos.documentoIdentidad())
                .monto(datos.monto())
                .plazo(datos.plazo())
                .tipoPrestamo(tipo)
                .estado(EstadoSolicitud.PENDIENTE_REVISION)
                .build();
    }

    private static String limpiar(String valor) {
        return valor == null ? null : valor.strip();
    }

    private static String limpiarTipo(String tipo) {
        return tipo == null ? null : tipo.strip().toUpperCase(Locale.ROOT);
    }
}
