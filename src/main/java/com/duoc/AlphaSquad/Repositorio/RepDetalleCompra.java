package com.duoc.AlphaSquad.Repositorio;

import com.duoc.AlphaSquad.Modelo.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepDetalleCompra extends JpaRepository<DetalleCompra, Long> {
}
