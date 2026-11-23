package co.com.crediya.jpa.solicitud;

import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SolicitudRepositoryAdapter implements SolicitudRepository {

    private final SolicitudJpaRepository jpa;

    @Override
    @Transactional
    public Solicitud guardar(Solicitud solicitud) {
        SolicitudData guardada = jpa.save(SolicitudMapper.toData(solicitud));
        log.info("Solicitud guardada con id {} en estado {}", guardada.getId(), guardada.getEstado());
        return SolicitudMapper.toDomain(guardada);
    }
}