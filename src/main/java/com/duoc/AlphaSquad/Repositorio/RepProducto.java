package com.duoc.AlphaSquad.Repositorio;

import com.duoc.AlphaSquad.Modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepProducto extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoria(String categoria);

    List<Producto> findBySubcategoria(String subcategoria);
}
