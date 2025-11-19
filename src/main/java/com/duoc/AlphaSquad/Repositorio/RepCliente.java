package com.duoc.AlphaSquad.Repositorio;

import com.duoc.AlphaSquad.Modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RepCliente extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByRut(String rut);
    Optional<Cliente> findByCorreo(String correo);
}
