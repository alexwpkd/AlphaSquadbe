package com.duoc.AlphaSquad.Controllador;

import com.duoc.AlphaSquad.Modelo.CarritoCompras;
import com.duoc.AlphaSquad.Modelo.DetalleCarrito;
import com.duoc.AlphaSquad.Modelo.Venta;
import com.duoc.AlphaSquad.Servicio.CarritoComprasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carritos")
// @CrossOrigin("*")
public class CarritoComprasController {

    private final CarritoComprasService service;

    public CarritoComprasController(CarritoComprasService service) {
        this.service = service;
    }

    // ============ CRUD básico ============

    @GetMapping
    public List<CarritoCompras> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarritoCompras> obtener(@PathVariable Long id) {
        CarritoCompras carrito = service.buscarPorId(id);
        return (carrito != null) ? ResponseEntity.ok(carrito) : ResponseEntity.notFound().build();
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<CarritoCompras> obtenerPorCliente(@PathVariable Long idCliente) {
        return service.buscarPorCliente(idCliente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CarritoCompras crear(@RequestBody CarritoCompras carrito) {
        return service.crear(carrito);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarritoCompras> actualizar(@PathVariable Long id,
                                                     @RequestBody CarritoCompras carrito) {
        CarritoCompras actualizado = service.actualizar(id, carrito);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ============ ENDPOINTS DE NEGOCIO ============

    @PostMapping("/{idCliente}/agregar")
    public ResponseEntity<DetalleCarrito> agregarProducto(
            @PathVariable Long idCliente,
            @RequestParam Long productoId,
            @RequestParam Integer cantidad) {

        DetalleCarrito detalle = service.agregarProductoAlCarrito(idCliente, productoId, cantidad);
        return ResponseEntity.ok(detalle);
    }

    @PutMapping("/{idCliente}/actualizar/{idDetalle}")
    public ResponseEntity<DetalleCarrito> actualizarCantidad(
            @PathVariable Long idCliente,
            @PathVariable Long idDetalle,
            @RequestParam Integer cantidad) {

        DetalleCarrito actualizado = service.actualizarCantidadProducto(idCliente, idDetalle, cantidad);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{idCliente}/eliminar-item/{idDetalle}")
    public ResponseEntity<Void> eliminarItem(
            @PathVariable Long idCliente,
            @PathVariable Long idDetalle) {

        service.eliminarItemDelCarrito(idCliente, idDetalle);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idCliente}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long idCliente) {
        service.vaciarCarrito(idCliente);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idCliente}/checkout")
    public ResponseEntity<Venta> checkout(@PathVariable Long idCliente) {
        Venta venta = service.checkout(idCliente);
        return ResponseEntity.ok(venta);
    }
}
