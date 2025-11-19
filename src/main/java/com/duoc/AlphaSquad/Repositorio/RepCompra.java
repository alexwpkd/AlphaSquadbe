package com.duoc.AlphaSquad.Repositorio;

import com.duoc.AlphaSquad.Modelo.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepCompra extends JpaRepository<Compra, Long> {
}
