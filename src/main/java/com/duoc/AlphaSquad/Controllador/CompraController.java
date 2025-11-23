package com.duoc.AlphaSquad.Controllador;

import com.duoc.AlphaSquad.Modelo.Compra;
import com.duoc.AlphaSquad.Servicio.CompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "*")
public class CompraController {

    private final CompraService service;

    public CompraController(CompraService service) {
        this.service = service;
    }

    @GetMapping
    public List<Compra> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> obtener(@PathVariable Long id) {
        Compra compra = service.buscarPorId(id);
        return (compra != null) ? ResponseEntity.ok(compra) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Compra crear(@RequestBody Compra compra) {
        return service.crear(compra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compra> actualizar(@PathVariable Long id,
                                             @RequestBody Compra compra) {
        Compra actualizado = service.actualizar(id, compra);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
