package com.duoc.AlphaSquad.Repositorio;

import com.duoc.AlphaSquad.Modelo.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RepAdmin extends JpaRepository<Administrador, Long> {
}

