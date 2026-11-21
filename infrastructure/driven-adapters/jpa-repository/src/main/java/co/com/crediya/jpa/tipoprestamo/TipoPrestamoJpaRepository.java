package co.com.crediya.jpa.tipoprestamo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoPrestamoJpaRepository extends JpaRepository<TipoPrestamoData, Long> {

    Optional<TipoPrestamoData> findByNombre(String nombre);
}