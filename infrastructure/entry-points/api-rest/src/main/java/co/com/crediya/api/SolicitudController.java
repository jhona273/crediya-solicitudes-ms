package co.com.crediya.api;

import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.usecase.registrarsolicitud.RegistrarSolicitudUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/solicitud", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SolicitudController {

    private final RegistrarSolicitudUseCase registrarSolicitudUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SolicitudResponse> registrar(@Valid @RequestBody RegistrarSolicitudRequest request) {
        log.info("Solicitud de préstamo recibida para el tipo {}", request.tipoPrestamo());
        Solicitud creada = registrarSolicitudUseCase.ejecutar(SolicitudApiMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(SolicitudApiMapper.toResponse(creada));
    }
}
