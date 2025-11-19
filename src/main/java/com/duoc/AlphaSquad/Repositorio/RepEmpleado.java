package com.duoc.AlphaSquad.Repositorio;

import com.duoc.AlphaSquad.Modelo.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepEmpleado extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByRut(String rut);
    Optional<Empleado> findByCorreo(String correo);
}
