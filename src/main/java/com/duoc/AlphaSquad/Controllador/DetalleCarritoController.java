package com.duoc.AlphaSquad.Controllador;

import com.duoc.AlphaSquad.Modelo.DetalleCarrito;
import com.duoc.AlphaSquad.Servicio.DetalleCarritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-carrito")
@CrossOrigin(origins = "*")
public class DetalleCarritoController {

    private final DetalleCarritoService service;

    public DetalleCarritoController(DetalleCarritoService service) {
        this.service = service;
    }

    @GetMapping
    public List<DetalleCarrito> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCarrito> obtener(@PathVariable Long id) {
        DetalleCarrito dc = service.buscarPorId(id);
        return (dc != null) ? ResponseEntity.ok(dc) : ResponseEntity.notFound().build();
    }

    @GetMapping("/carrito/{idCarrito}")
    public List<DetalleCarrito> listarPorCarrito(@PathVariable Long idCarrito) {
        return service.buscarPorCarrito(idCarrito);
    }

    @PostMapping
    public DetalleCarrito crear(@RequestBody DetalleCarrito detalle) {
        return service.crear(detalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleCarrito> actualizar(@PathVariable Long id,
                                                     @RequestBody DetalleCarrito detalle) {
        DetalleCarrito actualizado = service.actualizar(id, detalle);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
