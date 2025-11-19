package com.duoc.AlphaSquad.Repositorio;

import com.duoc.AlphaSquad.Modelo.Comuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepComuna extends JpaRepository<Comuna, Long> {
}
