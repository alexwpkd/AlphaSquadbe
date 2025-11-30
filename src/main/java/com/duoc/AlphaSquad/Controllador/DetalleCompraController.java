package com.duoc.AlphaSquad.Controllador;

import com.duoc.AlphaSquad.Modelo.DetalleCompra;
import com.duoc.AlphaSquad.Servicio.DetalleCompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-compra")
// @CrossOrigin("*")
public class DetalleCompraController {

    private final DetalleCompraService service;

    public DetalleCompraController(DetalleCompraService service) {
        this.service = service;
    }

    @GetMapping
    public List<DetalleCompra> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCompra> obtener(@PathVariable Long id) {
        DetalleCompra dc = service.buscarPorId(id);
        return (dc != null) ? ResponseEntity.ok(dc) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public DetalleCompra crear(@RequestBody DetalleCompra detalle) {
        return service.crear(detalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleCompra> actualizar(@PathVariable Long id,
                                                    @RequestBody DetalleCompra detalle) {
        DetalleCompra actualizado = service.actualizar(id, detalle);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
