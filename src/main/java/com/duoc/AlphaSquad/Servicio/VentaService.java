package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.Venta;
import com.duoc.AlphaSquad.Modelo.DetalleVenta;
import com.duoc.AlphaSquad.Modelo.Envio;
import com.duoc.AlphaSquad.Repositorio.RepAdmin;
import com.duoc.AlphaSquad.Repositorio.RepCliente;
import com.duoc.AlphaSquad.Repositorio.RepEmpleado;
import com.duoc.AlphaSquad.Repositorio.RepVenta;
import com.duoc.AlphaSquad.Repositorio.RepEnvio;
import com.duoc.AlphaSquad.Repositorio.RepDetalleVenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    // 🔹 Repos adicionales para borrar hijos antes de la venta
    @Autowired
    private RepEnvio repEnvio;

    @Autowired
    private RepDetalleVenta repDetalleVenta;

    // ================== CRUD BÁSICO ==================

    public List<Venta> listar() {
        return repVenta.findAll();
    }

    public Venta buscarPorId(Long id) {
        return repVenta.findById(id).orElse(null);
    }

    public Venta crear(Venta venta) {

        // Validaciones suaves (como las tenías)
        if (venta.getCliente() != null && venta.getCliente().getId() != null) {
            repCliente.findById(venta.getCliente().getId()).orElse(null);
        }

        if (venta.getAdministrador() != null
                && venta.getAdministrador().getIdAdministrador() != null) {
            repAdmin.findById(venta.getAdministrador().getIdAdministrador()).orElse(null);
        }

        if (venta.getEmpleado() != null && venta.getEmpleado().getId() != null) {
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

            // Opcional: actualizar también relaciones
            if (venta.getCliente() != null) {
                existente.setCliente(venta.getCliente());
            }
            if (venta.getAdministrador() != null) {
                existente.setAdministrador(venta.getAdministrador());
            }
            if (venta.getEmpleado() != null) {
                existente.setEmpleado(venta.getEmpleado());
            }

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

    // ================== ELIMINAR RESPETANDO FKs ==================

    public void eliminar(Long id) {

        // 1) Borrar envío asociado a la venta (si existe)
        repEnvio.findByVenta_IdVenta(id)
                .ifPresent(envio -> repEnvio.delete(envio));

        // 2) Borrar todos los detalles de venta asociados a la venta
        List<DetalleVenta> detalles = repDetalleVenta.findByVenta_IdVenta(id);
        if (!detalles.isEmpty()) {
            repDetalleVenta.deleteAll(detalles);
        }

        // 3) Borrar la venta en sí
        try {
            repVenta.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            // Si la venta no existe, simplemente no hacemos nada
        }
    }
}
