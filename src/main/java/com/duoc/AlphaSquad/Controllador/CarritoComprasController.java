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
    public ResponseEntity<?> agregarProducto(
            @PathVariable Long idCliente,
            @RequestParam Long productoId,
            @RequestParam Integer cantidad) {

        try {
            DetalleCarrito detalle = service.agregarProductoAlCarrito(idCliente, productoId, cantidad);
            return ResponseEntity.ok(detalle);
        } catch (IllegalArgumentException e) {
            // Errores de negocio → 400
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            // Error inesperado → 500 con detalle básico
            return ResponseEntity.status(500)
                    .body("Error interno al agregar producto al carrito: " + e.getMessage());
        }
    }

    @PutMapping("/{idCliente}/actualizar/{idDetalle}")
    public ResponseEntity<?> actualizarCantidad(
            @PathVariable Long idCliente,
            @PathVariable Long idDetalle,
            @RequestParam Integer cantidad) {

        try {
            DetalleCarrito actualizado = service.actualizarCantidadProducto(idCliente, idDetalle, cantidad);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Error interno al actualizar cantidad: " + e.getMessage());
        }
    }

    @DeleteMapping("/{idCliente}/eliminar-item/{idDetalle}")
    public ResponseEntity<?> eliminarItem(
            @PathVariable Long idCliente,
            @PathVariable Long idDetalle) {

        try {
            service.eliminarItemDelCarrito(idCliente, idDetalle);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Error interno al eliminar ítem: " + e.getMessage());
        }
    }

    @DeleteMapping("/{idCliente}/vaciar")
    public ResponseEntity<?> vaciarCarrito(@PathVariable Long idCliente) {
        try {
            service.vaciarCarrito(idCliente);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Error interno al vaciar carrito: " + e.getMessage());
        }
    }

    @PostMapping("/{idCliente}/checkout")
    public ResponseEntity<?> checkout(@PathVariable Long idCliente) {
        try {
            Venta venta = service.checkout(idCliente);
            return ResponseEntity.ok(venta);
        } catch (IllegalArgumentException e) {
            // ej: carrito vacío, carrito no existe, stock insuficiente, etc.
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Error interno en checkout: " + e.getMessage());
        }
    }
}
