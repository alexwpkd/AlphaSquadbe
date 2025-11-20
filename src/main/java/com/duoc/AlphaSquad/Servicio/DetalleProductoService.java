package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.DetalleProducto;
import com.duoc.AlphaSquad.Repositorio.RepDetalleProducto;
import com.duoc.AlphaSquad.Repositorio.RepProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleProductoService {

    @Autowired
    private RepDetalleProducto repDetalleProducto;

    @Autowired
    private RepProducto repProducto;

    public List<DetalleProducto> listar() {
        return repDetalleProducto.findAll();
    }

    public DetalleProducto buscarPorId(Long id) {
        return repDetalleProducto.findById(id).orElse(null);
    }

    public DetalleProducto crear(DetalleProducto detalle) {

        repProducto.findById(detalle.getProducto().getIdProducto()).orElse(null);

        return repDetalleProducto.save(detalle);
    }

    public DetalleProducto actualizar(Long id, DetalleProducto detalle) {
        DetalleProducto existente = buscarPorId(id);
        if (existente != null) {
            return repDetalleProducto.save(existente);
        }
        return null;
    }

    public void eliminar(Long id) {
        repDetalleProducto.deleteById(id);
    }
}
