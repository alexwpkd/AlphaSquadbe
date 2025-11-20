package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.Venta;
import com.duoc.AlphaSquad.Repositorio.RepAdmin;
import com.duoc.AlphaSquad.Repositorio.RepCliente;
import com.duoc.AlphaSquad.Repositorio.RepEmpleado;
import com.duoc.AlphaSquad.Repositorio.RepVenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {

    @Autowired
    private RepVenta repVenta;

    @Autowired
    private RepCliente repCliente;

    @Autowired
    private RepEmpleado repEmpleado;

    @Autowired
    private RepAdmin repAdmin;

    public List<Venta> listar() {
        return repVenta.findAll();
    }

    public Venta buscarPorId(Long id) {
        return repVenta.findById(id).orElse(null);
    }

    public Venta crear(Venta venta) {

        repCliente.findById(venta.getCliente().getId()).orElse(null);

        if (venta.getAdministrador() != null) {
            repAdmin.findById(venta.getAdministrador().getIdAdministrador()).orElse(null);
        }

        if (venta.getEmpleado() != null) {
            repEmpleado.findById(venta.getEmpleado().getId()).orElse(null);
        }

        return repVenta.save(venta);
    }

    public Venta actualizar(Long id, Venta venta) {
        Venta existente = buscarPorId(id);
        if (existente != null) {

            existente.setEstado(venta.getEstado());
            existente.setFechaVenta(venta.getFechaVenta());
            existente.setTotal(venta.getTotal());
            existente.setDescuento(venta.getDescuento());

            return repVenta.save(existente);
        }
        return null;
    }

    public List<Venta> buscarPorCliente(Long idCliente) {
        return repVenta.findByClienteId(idCliente);
    }

    public List<Venta> buscarPorEmpleado(Long idEmpleado) {
        return repVenta.findByEmpleadoId(idEmpleado);
    }

    public void eliminar(Long id) {
        repVenta.deleteById(id);
    }
}
