package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.*;
import com.duoc.AlphaSquad.Repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // ============= LÓGICA DE NEGOCIO =============

    /**
     * Obtiene el carrito ACTIVO del cliente.
     * Si no existe, lo crea.
     */
    public CarritoCompras obtenerOCrearCarritoActivo(Long idCliente) {
        // Intentamos obtener el carrito del cliente
        CarritoCompras carrito = repCarrito.findByClienteId(idCliente).orElse(null);

        // Si existe y está activo, lo usamos
        if (carrito != null && "activo".equalsIgnoreCase(carrito.getEstado())) {
            return carrito;
        }

        // Buscamos el cliente
        Cliente cliente = repCliente.findById(idCliente).orElse(null);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado con id: " + idCliente);
        }

        // Creamos un nuevo carrito activo
        CarritoCompras nuevo = new CarritoCompras();
        nuevo.setCliente(cliente);
        nuevo.setEstado("activo");
        nuevo.setFechaCreacion(LocalDateTime.now());

        return repCarrito.save(nuevo);
    }

    /**
     * Agrega un producto al carrito del cliente.
     * Si el carrito no existe, se crea.
     * Si el producto ya está en el carrito, se suma la cantidad.
     */
    public DetalleCarrito agregarProductoAlCarrito(Long idCliente, Long idProducto, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        CarritoCompras carrito = obtenerOCrearCarritoActivo(idCliente);

        Producto producto = repProducto.findById(idProducto).orElse(null);
        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado con id: " + idProducto);
        }

        // Buscamos si ya existe un detalle de ese producto en el carrito
        List<DetalleCarrito> detalles = repDetalleCarrito.findByCarrito_IdCarrito(carrito.getIdCarrito());
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
            return repDetalleCarrito.save(nuevo);
        } else {
            existente.setCantidad(existente.getCantidad() + cantidad);
            return repDetalleCarrito.save(existente);
        }
    }

    /**
     * Actualiza la cantidad de un producto específico dentro del carrito del cliente.
     */
    public DetalleCarrito actualizarCantidadProducto(Long idCliente, Long idDetalle, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        CarritoCompras carrito = repCarrito.findByClienteId(idCliente).orElse(null);
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
        return repDetalleCarrito.save(detalle);
    }

    /**
     * Elimina un ítem específico del carrito del cliente.
     */
    public void eliminarItemDelCarrito(Long idCliente, Long idDetalle) {
        CarritoCompras carrito = repCarrito.findByClienteId(idCliente).orElse(null);
        if (carrito == null) {
            throw new IllegalArgumentException("Carrito no encontrado para el cliente id: " + idCliente);
        }

        DetalleCarrito detalle = repDetalleCarrito.findById(idDetalle).orElse(null);
        if (detalle == null || !detalle.getCarrito().getIdCarrito().equals(carrito.getIdCarrito())) {
            throw new IllegalArgumentException("El detalle no pertenece al carrito del cliente");
        }

        repDetalleCarrito.deleteById(idDetalle);
    }

    /**
     * Elimina todos los ítems del carrito del cliente.
     */
    public void vaciarCarrito(Long idCliente) {
        CarritoCompras carrito = repCarrito.findByClienteId(idCliente).orElse(null);
        if (carrito == null) {
            throw new IllegalArgumentException("Carrito no encontrado para el cliente id: " + idCliente);
        }

        List<DetalleCarrito> detalles = repDetalleCarrito.findByCarrito_IdCarrito(carrito.getIdCarrito());
        repDetalleCarrito.deleteAll(detalles);
    }

    /**
     * Convierte el carrito del cliente en una venta:
     *  - Crea Venta
     *  - Crea DetalleVenta
     *  - Descuenta stock
     *  - Marca carrito como convertido_en_venta
     *  - Vacía el carrito
     */
    public Venta checkout(Long idCliente) {

        CarritoCompras carrito = repCarrito.findByClienteId(idCliente).orElse(null);
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

        // Primero guardamos la venta para tener idVenta
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

        return venta;
    }
}
