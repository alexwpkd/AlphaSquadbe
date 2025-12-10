package com.duoc.AlphaSquad.Repositorio;

import com.duoc.AlphaSquad.Modelo.CarritoCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepCarritoCompra extends JpaRepository<CarritoCompras, Long> {

    /**
     * Busca el carrito por el ID del cliente.
     * Usa la propiedad "cliente" de CarritoCompras y la propiedad "id" de Cliente:
     * path = cliente.id → cliente_Id
     */
    Optional<CarritoCompras> findByCliente_Id(Long idCliente);
}
