package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.CarritoCompras;
import com.duoc.AlphaSquad.Modelo.Cliente;
import com.duoc.AlphaSquad.Modelo.DetalleCarrito;
import com.duoc.AlphaSquad.Modelo.DetalleVenta;
import com.duoc.AlphaSquad.Modelo.Producto;
import com.duoc.AlphaSquad.Modelo.Venta;
import com.duoc.AlphaSquad.Repositorio.RepCarritoCompra;
import com.duoc.AlphaSquad.Repositorio.RepCliente;
import com.duoc.AlphaSquad.Repositorio.RepDetalleCarrito;
import com.duoc.AlphaSquad.Repositorio.RepDetalleVenta;
import com.duoc.AlphaSquad.Repositorio.RepProducto;
import com.duoc.AlphaSquad.Repositorio.RepVenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoComprasService {

    @Autowired
    private RepCarritoCompra repCarrito;

    @Autowired
    private RepCliente repCliente;

    @Autowired
    private RepDetalleCarrito repDetalleCarrito;

    @Autowired
    private RepProducto repProducto;

    @Autowired
    private RepVenta repVenta;

    @Autowired
    private RepDetalleVenta repDetalleVenta;

    // ================= CRUD básico =================

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

        if (carrito.getFechaCreacion() == null) {
            carrito.setFechaCreacion(LocalDateTime.now());
        }
        if (carrito.getEstado() == null) {
            carrito.setEstado("activo");
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
        return repCarrito.findByCliente_Id(idCliente);
    }

    public void eliminar(Long id) {
        repCarrito.deleteById(id);
    }

    // ============= LÓGICA DE NEGOCIO =============

    /**
     * Obtiene el carrito del cliente.
     * - Si NO existe → lo crea.
     * - Si existe con otro estado → lo pone "activo" y lo reutiliza.
     * Nunca crea dos carritos para el mismo cliente (por el UNIQUE en cliente_id).
     */
    @Transactional
    public CarritoCompras obtenerOCrearCarritoActivo(Long idCliente) {
        System.out.println("[CarritoService] Buscando carrito para cliente id=" + idCliente);

        CarritoCompras carrito = repCarrito.findByCliente_Id(idCliente).orElse(null);

        if (carrito != null) {
            System.out.println("[CarritoService] Carrito encontrado (id=" + carrito.getIdCarrito() +
                    "), estado=" + carrito.getEstado());

            if (!"activo".equalsIgnoreCase(carrito.getEstado())) {
                carrito.setEstado("activo");
                carrito = repCarrito.save(carrito);
                System.out.println("[CarritoService] Estado de carrito actualizado a 'activo'");
            }

            return carrito;
        }

        // No existe carrito → creamos uno nuevo
        Cliente cliente = repCliente.findById(idCliente).orElse(null);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado con id: " + idCliente);
        }

        CarritoCompras nuevo = new CarritoCompras();
        nuevo.setCliente(cliente);
        nuevo.setEstado("activo");
        nuevo.setFechaCreacion(LocalDateTime.now());

        CarritoCompras guardado = repCarrito.save(nuevo);
        System.out.println("[CarritoService] Carrito nuevo creado: idCarrito=" + guardado.getIdCarrito());
        return guardado;
    }

    /**
     * Agrega un producto al carrito del cliente.
     */
    @Transactional
    public DetalleCarrito agregarProductoAlCarrito(Long idCliente, Long idProducto, Integer cantidad) {
        System.out.println("[CarritoService] agregarProductoAlCarrito -> cliente=" + idCliente +
                ", producto=" + idProducto + ", cantidad=" + cantidad);

        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        CarritoCompras carrito = obtenerOCrearCarritoActivo(idCliente);

        Producto producto = repProducto.findById(idProducto).orElse(null);
        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado con id: " + idProducto);
        }

        System.out.println("[CarritoService] Producto encontrado: " + producto.getNombre() +
                " (stock=" + producto.getStock() + ")");

        List<DetalleCarrito> detalles = repDetalleCarrito.findByCarrito_IdCarrito(carrito.getIdCarrito());
        System.out.println("[CarritoService] Detalles actuales en carrito: " + detalles.size());

        DetalleCarrito existente = null;

        for (DetalleCarrito d : detalles) {
            if (d.getProducto().getIdProducto().equals(idProducto)) {
                existente = d;
                break;
            }
        }

        int totalSolicitado = cantidad;
        if (existente != null) {
            totalSolicitado += existente.getCantidad();
        }

        if (producto.getStock() != null && producto.getStock() < totalSolicitado) {
            throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        if (existente == null) {
            DetalleCarrito nuevo = new DetalleCarrito();
            nuevo.setCarrito(carrito);
            nuevo.setProducto(producto);
            nuevo.setCantidad(cantidad);

            DetalleCarrito guardado = repDetalleCarrito.save(nuevo);
            System.out.println("[CarritoService] Detalle nuevo creado: idDetalle=" + guardado.getIdCarritoDetalle());
            return guardado;
        } else {
            existente.setCantidad(existente.getCantidad() + cantidad);
            DetalleCarrito actualizado = repDetalleCarrito.save(existente);
            System.out.println("[CarritoService] Detalle actualizado: idDetalle=" +
                    actualizado.getIdCarritoDetalle() + ", nuevaCantidad=" + actualizado.getCantidad());
            return actualizado;
        }
    }

    /**
     * Actualiza la cantidad de un producto específico dentro del carrito del cliente.
     */
    @Transactional
    public DetalleCarrito actualizarCantidadProducto(Long idCliente, Long idDetalle, Integer cantidad) {
        System.out.println("[CarritoService] actualizarCantidadProducto -> cliente=" + idCliente +
                ", detalle=" + idDetalle + ", cantidad=" + cantidad);

        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        CarritoCompras carrito = repCarrito.findByCliente_Id(idCliente).orElse(null);
        if (carrito == null) {
            throw new IllegalArgumentException("Carrito no encontrado para el cliente id: " + idCliente);
        }

        DetalleCarrito detalle = repDetalleCarrito.findById(idDetalle).orElse(null);
        if (detalle == null || !detalle.getCarrito().getIdCarrito().equals(carrito.getIdCarrito())) {
            throw new IllegalArgumentException("El detalle no pertenece al carrito del cliente");
        }

        Producto producto = detalle.getProducto();
        if (producto.getStock() != null && producto.getStock() < cantidad) {
            throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        detalle.setCantidad(cantidad);
        DetalleCarrito actualizado = repDetalleCarrito.save(detalle);
        System.out.println("[CarritoService] Cantidad actualizada en detalle=" + actualizado.getIdCarritoDetalle() +
                ", cantidad=" + actualizado.getCantidad());
        return actualizado;
    }

    /**
     * Elimina un ítem específico del carrito del cliente.
     */
    @Transactional
    public void eliminarItemDelCarrito(Long idCliente, Long idDetalle) {
        System.out.println("[CarritoService] eliminarItemDelCarrito -> cliente=" + idCliente +
                ", detalle=" + idDetalle);

        CarritoCompras carrito = repCarrito.findByCliente_Id(idCliente).orElse(null);
        if (carrito == null) {
            throw new IllegalArgumentException("Carrito no encontrado para el cliente id: " + idCliente);
        }

        DetalleCarrito detalle = repDetalleCarrito.findById(idDetalle).orElse(null);
        if (detalle == null || !detalle.getCarrito().getIdCarrito().equals(carrito.getIdCarrito())) {
            throw new IllegalArgumentException("El detalle no pertenece al carrito del cliente");
        }

        repDetalleCarrito.deleteById(idDetalle);
        System.out.println("[CarritoService] Detalle eliminado correctamente.");
    }

    /**
     * Elimina todos los ítems del carrito del cliente.
     */
    @Transactional
    public void vaciarCarrito(Long idCliente) {
        System.out.println("[CarritoService] vaciarCarrito -> cliente=" + idCliente);

        CarritoCompras carrito = repCarrito.findByCliente_Id(idCliente).orElse(null);
        if (carrito == null) {
            throw new IllegalArgumentException("Carrito no encontrado para el cliente id: " + idCliente);
        }

        List<DetalleCarrito> detalles = repDetalleCarrito.findByCarrito_IdCarrito(carrito.getIdCarrito());
        repDetalleCarrito.deleteAll(detalles);
        System.out.println("[CarritoService] Carrito vaciado. Items eliminados: " + detalles.size());
    }

    /**
     * Convierte el carrito del cliente en una venta.
     */
    @Transactional
    public Venta checkout(Long idCliente) {
        System.out.println("[CarritoService] checkout -> cliente=" + idCliente);

        CarritoCompras carrito = repCarrito.findByCliente_Id(idCliente).orElse(null);
        if (carrito == null) {
            throw new IllegalArgumentException("Carrito no encontrado para el cliente id: " + idCliente);
        }

        List<DetalleCarrito> detallesCarrito = repDetalleCarrito.findByCarrito_IdCarrito(carrito.getIdCarrito());
        if (detallesCarrito.isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío");
        }

        Cliente cliente = carrito.getCliente();
        if (cliente == null) {
            cliente = repCliente.findById(idCliente).orElse(null);
            if (cliente == null) {
                throw new IllegalArgumentException("Cliente no encontrado con id: " + idCliente);
            }
        }

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFechaVenta(LocalDateTime.now());
        venta.setEstado("pagada");
        venta.setDescuento(0);
        venta.setTotal(0);

        venta = repVenta.save(venta);

        int total = 0;

        for (DetalleCarrito dc : detallesCarrito) {
            Producto producto = dc.getProducto();

            Integer stockActual = producto.getStock();
            if (stockActual != null && stockActual < dc.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            if (stockActual != null) {
                producto.setStock(stockActual - dc.getCantidad());
                if (producto.getStock() <= 0) {
                    producto.setEnStock(false);
                }
                repProducto.save(producto);
            }

            DetalleVenta dv = new DetalleVenta();
            dv.setVenta(venta);
            dv.setProducto(producto);
            dv.setCantidad(dc.getCantidad());
            dv.setPrecioUnitario(producto.getPrecio());

            int subtotal = dc.getCantidad() * producto.getPrecio();
            dv.setSubtotal(subtotal);

            total += subtotal;
            repDetalleVenta.save(dv);
        }

        venta.setTotal(total);
        venta = repVenta.save(venta);

        carrito.setEstado("convertido_en_venta");
        repCarrito.save(carrito);
        repDetalleCarrito.deleteAll(detallesCarrito);

        System.out.println("[CarritoService] Checkout completado. idVenta=" + venta.getIdVenta() +
                ", total=" + total);

        return venta;
    }
}
