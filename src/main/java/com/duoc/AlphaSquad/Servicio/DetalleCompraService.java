package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.DetalleCompra;
import com.duoc.AlphaSquad.Repositorio.RepCompra;
import com.duoc.AlphaSquad.Repositorio.RepDetalleCompra;
import com.duoc.AlphaSquad.Repositorio.RepProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleCompraService {

    @Autowired
    private RepDetalleCompra repDetalleCompra;

    @Autowired
    private RepCompra repCompra;

    @Autowired
    private RepProducto repProducto;

    public List<DetalleCompra> listar() {
        return repDetalleCompra.findAll();
    }

    public DetalleCompra buscarPorId(Long id) {
        return repDetalleCompra.findById(id).orElse(null);
    }

    public DetalleCompra crear(DetalleCompra detalle) {

        repCompra.findById(detalle.getCompra().getIdCompra()).orElse(null);
        repProducto.findById(detalle.getProducto().getIdProducto()).orElse(null);

        return repDetalleCompra.save(detalle);
    }

    public DetalleCompra actualizar(Long id, DetalleCompra detalle) {
        DetalleCompra existente = buscarPorId(id);
        if (existente != null) {

            existente.setCantidad(detalle.getCantidad());
            existente.setPrecioUnitario(detalle.getPrecioUnitario());
            existente.setSubtotal(detalle.getSubtotal());

            return repDetalleCompra.save(existente);
        }
        return null;
    }

    public void eliminar(Long id) {
        repDetalleCompra.deleteById(id);
    }
}
