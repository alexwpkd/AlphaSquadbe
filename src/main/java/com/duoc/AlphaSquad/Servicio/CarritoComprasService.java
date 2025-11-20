package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.CarritoCompras;
import com.duoc.AlphaSquad.Repositorio.RepCarritoCompra;
import com.duoc.AlphaSquad.Repositorio.RepCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoComprasService {

    @Autowired
    private RepCarritoCompra repCarrito;

    @Autowired
    private RepCliente repCliente;

    public List<CarritoCompras> listar() {
        return repCarrito.findAll();
    }

    public CarritoCompras buscarPorId(Long id) {
        return repCarrito.findById(id).orElse(null);
    }

    public CarritoCompras crear(CarritoCompras carrito) {

        if (carrito.getCliente() != null) {
            repCliente.findById(carrito.getCliente().getId()).orElse(null);
        }

        return repCarrito.save(carrito);
    }

    public CarritoCompras actualizar(Long id, CarritoCompras carrito) {
        CarritoCompras existente = buscarPorId(id);
        if (existente != null) {

            existente.setEstado(carrito.getEstado());
            existente.setFechaCreacion(carrito.getFechaCreacion());

            return repCarrito.save(existente);
        }
        return null;
    }

    public Optional<CarritoCompras> buscarPorCliente(Long idCliente) {
        return repCarrito.findByClienteId(idCliente);
    }

    public void eliminar(Long id) {
        repCarrito.deleteById(id);
    }
}
