package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.DetalleVenta;
import com.duoc.AlphaSquad.Repositorio.RepDetalleVenta;
import com.duoc.AlphaSquad.Repositorio.RepProducto;
import com.duoc.AlphaSquad.Repositorio.RepVenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleVentaService {

    @Autowired
    private RepDetalleVenta repDetalleVenta;

    @Autowired
    private RepVenta repVenta;

    @Autowired
    private RepProducto repProducto;

    public List<DetalleVenta> listar() {
        return repDetalleVenta.findAll();
    }

    public DetalleVenta buscarPorId(Long id) {
        return repDetalleVenta.findById(id).orElse(null);
    }

    public DetalleVenta crear(DetalleVenta detalle) {

        repVenta.findById(detalle.getVenta().getIdVenta()).orElse(null);
        repProducto.findById(detalle.getProducto().getIdProducto()).orElse(null);

        return repDetalleVenta.save(detalle);
    }

    public DetalleVenta actualizar(Long id, DetalleVenta detalle) {
        DetalleVenta existente = buscarPorId(id);
        if (existente != null) {

            existente.setCantidad(detalle.getCantidad());
            existente.setPrecioUnitario(detalle.getPrecioUnitario());
            existente.setSubtotal(detalle.getSubtotal());

            return repDetalleVenta.save(existente);
        }
        return null;
    }

    public List<DetalleVenta> buscarPorVenta(Long idVenta) {
        return repDetalleVenta.findByVenta_IdVenta(idVenta);
    }

    public void eliminar(Long id) {
        repDetalleVenta.deleteById(id);
    }
}

