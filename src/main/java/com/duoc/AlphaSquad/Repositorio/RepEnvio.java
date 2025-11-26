package com.duoc.AlphaSquad.Repositorio;

import com.duoc.AlphaSquad.Modelo.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepEnvio extends JpaRepository<Envio, Long> {
    Optional<Envio> findByVenta_IdVenta(Long idVenta);
}

