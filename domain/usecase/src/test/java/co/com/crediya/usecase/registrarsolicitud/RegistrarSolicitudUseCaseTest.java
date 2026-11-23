package co.com.crediya.usecase.registrarsolicitud;

import co.com.crediya.model.solicitud.EstadoSolicitud;
import co.com.crediya.model.solicitud.NuevaSolicitud;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.SolicitudInvalidaException;
import co.com.crediya.model.solicitud.TipoPrestamoNoExisteException;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import co.com.crediya.model.tipoprestamo.TipoPrestamo;
import co.com.crediya.model.tipoprestamo.gateways.TipoPrestamoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrarSolicitudUseCaseTest {

    private static final String DOCUMENTO = "1020304050";
    private static final String TIPO = "LIBRE_INVERSION";

    @Mock
    private SolicitudRepository solicitudRepository;

    @Mock
    private TipoPrestamoRepository tipoPrestamoRepository;

    @InjectMocks
    private RegistrarSolicitudUseCase useCase;

    private TipoPrestamo tipoLibreInversion() {
        return TipoPrestamo.builder()
                .id(1L)
                .nombre(TIPO)
                .montoMinimo(new BigDecimal("1000000"))
                .montoMaximo(new BigDecimal("50000000"))
                .tasaInteres(new BigDecimal("18.50"))
                .validacionAutomatica(true)
                .build();
    }

    @Test
    void ejecutar_conDatosValidos_guardaSolicitudPendienteDeRevision() {
        TipoPrestamo tipo = tipoLibreInversion();
        when(tipoPrestamoRepository.buscarPorNombre(TIPO)).thenReturn(Optional.of(tipo));
        when(solicitudRepository.guardar(any(Solicitud.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

        Solicitud resultado = useCase.ejecutar(
                new NuevaSolicitud(DOCUMENTO, new BigDecimal("5000000"), 24, TIPO));

        assertThat(resultado.getEstado()).isEqualTo(EstadoSolicitud.PENDIENTE_REVISION);
        assertThat(resultado.getTipoPrestamo()).isEqualTo(tipo);
        assertThat(resultado.getDocumentoIdentidad()).isEqualTo(DOCUMENTO);
        assertThat(resultado.getPlazo()).isEqualTo(24);
        assertThat(resultado.getMonto()).isEqualByComparingTo("5000000");
        verify(solicitudRepository).guardar(any(Solicitud.class));
    }

    @Test
    void ejecutar_conTipoEnMinusculaYEspacios_normalizaLosDatosAntesDeUsarlos() {
        when(tipoPrestamoRepository.buscarPorNombre(TIPO)).thenReturn(Optional.of(tipoLibreInversion()));
        when(solicitudRepository.guardar(any(Solicitud.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

        useCase.ejecutar(new NuevaSolicitud("  " + DOCUMENTO + " ", new BigDecimal("5000000"), 24, "  libre_inversion "));

        verify(tipoPrestamoRepository).buscarPorNombre(TIPO);
        ArgumentCaptor<Solicitud> captor = ArgumentCaptor.forClass(Solicitud.class);
        verify(solicitudRepository).guardar(captor.capture());
        assertThat(captor.getValue().getDocumentoIdentidad()).isEqualTo(DOCUMENTO);
    }

    @Test
    void ejecutar_conTipoInexistente_lanzaExcepcionYNoGuarda() {
        when(tipoPrestamoRepository.buscarPorNombre("NO_EXISTE")).thenReturn(Optional.empty());
        NuevaSolicitud datos = new NuevaSolicitud(DOCUMENTO, new BigDecimal("5000000"), 24, "NO_EXISTE");

        assertThatThrownBy(() -> useCase.ejecutar(datos))
                .isInstanceOf(TipoPrestamoNoExisteException.class)
                .hasMessageContaining("NO_EXISTE");
        verify(solicitudRepository, never()).guardar(any(Solicitud.class));
    }

    @Test
    void ejecutar_conMontoFueraDeRango_lanzaExcepcionYNoGuarda() {
        when(tipoPrestamoRepository.buscarPorNombre(TIPO)).thenReturn(Optional.of(tipoLibreInversion()));
        NuevaSolicitud datos = new NuevaSolicitud(DOCUMENTO, new BigDecimal("100000000"), 24, TIPO);

        assertThatThrownBy(() -> useCase.ejecutar(datos))
                .isInstanceOf(SolicitudInvalidaException.class)
                .hasMessageContaining("debe estar entre");
        verify(solicitudRepository, never()).guardar(any(Solicitud.class));
    }

    @Test
    void ejecutar_conDocumentoInvalido_lanzaExcepcionSinConsultarNada() {
        NuevaSolicitud datos = new NuevaSolicitud("abc", new BigDecimal("5000000"), 24, TIPO);

        assertThatThrownBy(() -> useCase.ejecutar(datos))
                .isInstanceOf(SolicitudInvalidaException.class);
        verifyNoInteractions(solicitudRepository, tipoPrestamoRepository);
    }

    @Test
    void ejecutar_conSolicitudNula_lanzaExcepcionSinConsultarNada() {
        assertThatThrownBy(() -> useCase.ejecutar(null))
                .isInstanceOf(SolicitudInvalidaException.class);
        verifyNoInteractions(solicitudRepository, tipoPrestamoRepository);
    }
}
