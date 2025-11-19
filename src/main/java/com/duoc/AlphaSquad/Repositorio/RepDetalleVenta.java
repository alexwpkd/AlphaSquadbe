package com.duoc.AlphaSquad.Repositorio;

import com.duoc.AlphaSquad.Modelo.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepDetalleVenta extends JpaRepository<DetalleVenta, Long> {
    // Antes: List<DetalleVenta> findByVentaId(Long ventaId);
    List<DetalleVenta> findByVenta_IdVenta(Long idVenta);
}
