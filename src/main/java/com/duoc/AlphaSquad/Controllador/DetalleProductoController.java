package com.duoc.AlphaSquad.Controllador;

import com.duoc.AlphaSquad.Modelo.DetalleProducto;
import com.duoc.AlphaSquad.Servicio.DetalleProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-producto")
// @CrossOrigin("*")
public class DetalleProductoController {

    private final DetalleProductoService service;

    public DetalleProductoController(DetalleProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<DetalleProducto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleProducto> obtener(@PathVariable Long id) {
        DetalleProducto dp = service.buscarPorId(id);
        return (dp != null) ? ResponseEntity.ok(dp) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public DetalleProducto crear(@RequestBody DetalleProducto detalle) {
        return service.crear(detalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleProducto> actualizar(@PathVariable Long id,
                                                      @RequestBody DetalleProducto detalle) {
        DetalleProducto actualizado = service.actualizar(id, detalle);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
