package com.duoc.AlphaSquad.Repositorio;

import com.duoc.AlphaSquad.Modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepProducto extends JpaRepository<Producto, Long> {
    Optional<Producto> findBySku(String sku);
    List<Producto> findByCategoria(String categoria);
    List<Producto> findBySubcategoria(String subcategoria);
}
