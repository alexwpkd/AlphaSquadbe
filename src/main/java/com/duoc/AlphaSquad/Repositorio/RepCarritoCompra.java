package com.duoc.AlphaSquad.Repositorio;

import com.duoc.AlphaSquad.Modelo.CarritoCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepCarritoCompra extends JpaRepository<CarritoCompras, Long> {
    Optional<CarritoCompras> findByClienteId(Long clienteId);
}
