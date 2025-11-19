package com.duoc.AlphaSquad.Repositorio;

import com.duoc.AlphaSquad.Modelo.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepVenta extends JpaRepository<Venta, Long> {
    List<Venta> findByClienteId(Long clienteId);
    List<Venta> findByEmpleadoId(Long empleadoId);
}
