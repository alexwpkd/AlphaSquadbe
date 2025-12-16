package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.Cliente;
import com.duoc.AlphaSquad.Modelo.Comuna;
import com.duoc.AlphaSquad.Modelo.CarritoCompras;
import com.duoc.AlphaSquad.Modelo.Venta;
import com.duoc.AlphaSquad.Modelo.Envio;
import com.duoc.AlphaSquad.Modelo.DetalleCarrito;
import com.duoc.AlphaSquad.Modelo.DetalleVenta;
import com.duoc.AlphaSquad.Repositorio.RepCliente;
import com.duoc.AlphaSquad.Repositorio.RepComuna;
import com.duoc.AlphaSquad.Repositorio.RepCarritoCompra;
import com.duoc.AlphaSquad.Repositorio.RepDetalleCarrito;
import com.duoc.AlphaSquad.Repositorio.RepVenta;
import com.duoc.AlphaSquad.Repositorio.RepDetalleVenta;
import com.duoc.AlphaSquad.Repositorio.RepEnvio;
import com.duoc.AlphaSquad.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final RepCliente repCliente;
    private final RepComuna repComuna;
    private final RepCarritoCompra repCarrito;
    private final RepDetalleCarrito repDetalleCarrito;
    private final RepVenta repVenta;
    private final RepDetalleVenta repDetalleVenta;
    private final RepEnvio repEnvio;

    public ClienteService(RepCliente repCliente,
                          RepComuna repComuna,
                          RepCarritoCompra repCarrito,
                          RepDetalleCarrito repDetalleCarrito,
                          RepVenta repVenta,
                          RepDetalleVenta repDetalleVenta,
                          RepEnvio repEnvio) {
        this.repCliente = repCliente;
        this.repComuna = repComuna;
        this.repCarrito = repCarrito;
        this.repDetalleCarrito = repDetalleCarrito;
        this.repVenta = repVenta;
        this.repDetalleVenta = repDetalleVenta;
        this.repEnvio = repEnvio;
    }

    // ===== CRUD BÁSICO =====

    public List<Cliente> listar() {
        return repCliente.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return repCliente.findById(id).orElse(null);
    }

    public Cliente crear(Cliente cliente) {

        // ⚠️ Manejo seguro de COMUNA
        if (cliente.getComuna() != null && cliente.getComuna().getIdComuna() != null) {
            Long idComuna = cliente.getComuna().getIdComuna();

            Comuna comuna = repComuna.findById(idComuna)
                    .orElseThrow(() -> new ResourceNotFoundException("Comuna no encontrada: " + idComuna));

            cliente.setComuna(comuna);
        } else {
            cliente.setComuna(null);
        }

        // ID auto-increment, no viene desde el front
        return repCliente.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente nuevo) {
        return repCliente.findById(id).map(actual -> {

            actual.setNombre(nuevo.getNombre());
            actual.setApellidos(nuevo.getApellidos());
            actual.setRut(nuevo.getRut());
            actual.setCorreo(nuevo.getCorreo());
            actual.setPassword(nuevo.getPassword());
            actual.setDireccion(nuevo.getDireccion());

            // ⚠️ Manejo seguro de COMUNA también en actualización
            if (nuevo.getComuna() != null && nuevo.getComuna().getIdComuna() != null) {
                Long idComuna = nuevo.getComuna().getIdComuna();
                Comuna comuna = repComuna.findById(idComuna)
                        .orElseThrow(() -> new ResourceNotFoundException("Comuna no encontrada: " + idComuna));
                actual.setComuna(comuna);
            } else {
                actual.setComuna(null);
            }

            return repCliente.save(actual);
        }).orElse(null);
    }

    /**
     * Elimina un cliente limpiando primero todas las referencias:
     *  - carrito_compra + detalle_carrito
     *  - ventas + detalle_venta + envíos
     */
    public void eliminar(Long id) {

        Cliente cliente = repCliente.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado: " + id));

        // 1) Carrito + DetalleCarrito
        repCarrito.findByCliente_Id(id).ifPresent(carrito -> {
            List<DetalleCarrito> detalles = repDetalleCarrito.findByCarrito_IdCarrito(carrito.getIdCarrito());
            if (!detalles.isEmpty()) {
                repDetalleCarrito.deleteAll(detalles);
            }
            repCarrito.delete(carrito);
        });

        // 2) Ventas + DetalleVenta + Envios
        List<Venta> ventas = repVenta.findByClienteId(id);
        for (Venta v : ventas) {

            // 2.1) Envío asociado a la venta (si existe)
            repEnvio.findByVenta_IdVenta(v.getIdVenta())
                    .ifPresent(repEnvio::delete);

            // 2.2) DetalleVenta asociados
            List<DetalleVenta> detVentas = repDetalleVenta.findByVenta_IdVenta(v.getIdVenta());
            if (!detVentas.isEmpty()) {
                repDetalleVenta.deleteAll(detVentas);
            }

            // 2.3) Venta
            repVenta.delete(v);
        }

        // 3) Finalmente el cliente
        repCliente.delete(cliente);
    }

    // ===== BÚSQUEDAS ESPECÍFICAS =====

    public Optional<Cliente> buscarPorCorreo(String correo) {
        return repCliente.findByCorreo(correo);
    }

    public Optional<Cliente> buscarPorRut(String rut) {
        return repCliente.findByRut(rut);
    }
}
