package com.duoc.AlphaSquad.Repositorio;

import com.duoc.AlphaSquad.Modelo.DetalleCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepDetalleCarrito extends JpaRepository<DetalleCarrito, Long> {
    // Antes: List<DetalleCarrito> findByCarritoId(Long carritoId);
    List<DetalleCarrito> findByCarrito_IdCarrito(Long idCarrito);
}
