package com.duoc.AlphaSquad.Controllador;

import com.duoc.AlphaSquad.Modelo.DetalleVenta;
import com.duoc.AlphaSquad.Servicio.DetalleVentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-venta")
@CrossOrigin(origins = "*")
public class DetalleVentaController {

    private final DetalleVentaService service;

    public DetalleVentaController(DetalleVentaService service) {
        this.service = service;
    }

    @GetMapping
    public List<DetalleVenta> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVenta> obtener(@PathVariable Long id) {
        DetalleVenta dv = service.buscarPorId(id);
        return (dv != null) ? ResponseEntity.ok(dv) : ResponseEntity.notFound().build();
    }

    @GetMapping("/venta/{idVenta}")
    public List<DetalleVenta> listarPorVenta(@PathVariable Long idVenta) {
        return service.buscarPorVenta(idVenta);
    }

    @PostMapping
    public DetalleVenta crear(@RequestBody DetalleVenta detalle) {
        return service.crear(detalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleVenta> actualizar(@PathVariable Long id,
                                                   @RequestBody DetalleVenta detalle) {
        DetalleVenta actualizado = service.actualizar(id, detalle);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
