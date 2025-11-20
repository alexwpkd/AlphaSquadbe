package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.DetalleCarrito;
import com.duoc.AlphaSquad.Repositorio.RepCarritoCompra;
import com.duoc.AlphaSquad.Repositorio.RepDetalleCarrito;
import com.duoc.AlphaSquad.Repositorio.RepProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleCarritoService {

    @Autowired
    private RepDetalleCarrito repDetalleCarrito;

    @Autowired
    private RepCarritoCompra repCarrito;

    @Autowired
    private RepProducto repProducto;

    public List<DetalleCarrito> listar() {
        return repDetalleCarrito.findAll();
    }

    public DetalleCarrito buscarPorId(Long id) {
        return repDetalleCarrito.findById(id).orElse(null);
    }

    public DetalleCarrito crear(DetalleCarrito detalle) {

        repCarrito.findById(detalle.getCarrito().getIdCarrito()).orElse(null);
        repProducto.findById(detalle.getProducto().getIdProducto()).orElse(null);

        return repDetalleCarrito.save(detalle);
    }

    public DetalleCarrito actualizar(Long id, DetalleCarrito detalle) {
        DetalleCarrito existente = buscarPorId(id);
        if (existente != null) {
            existente.setCantidad(detalle.getCantidad());
            return repDetalleCarrito.save(existente);
        }
        return null;
    }

    public List<DetalleCarrito> buscarPorCarrito(Long idCarrito) {
        return repDetalleCarrito.findByCarrito_IdCarrito(idCarrito);
    }

    public void eliminar(Long id) {
        repDetalleCarrito.deleteById(id);
    }
}
