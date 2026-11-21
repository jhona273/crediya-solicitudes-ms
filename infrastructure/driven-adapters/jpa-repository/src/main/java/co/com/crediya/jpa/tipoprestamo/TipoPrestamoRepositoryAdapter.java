package co.com.crediya.jpa.tipoprestamo;

import co.com.crediya.model.tipoprestamo.TipoPrestamo;
import co.com.crediya.model.tipoprestamo.gateways.TipoPrestamoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TipoPrestamoRepositoryAdapter implements TipoPrestamoRepository {

    private final TipoPrestamoJpaRepository jpa;

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoPrestamo> buscarPorNombre(String nombre) {
        return jpa.findByNombre(nombre).map(TipoPrestamoMapper::toDomain);
    }
}