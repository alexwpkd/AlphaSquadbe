package com.duoc.AlphaSquad.Repositorio;

import com.duoc.AlphaSquad.Modelo.DetalleProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepDetalleProducto extends JpaRepository<DetalleProducto, Long> {
}
