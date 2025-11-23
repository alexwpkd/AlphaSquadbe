package com.duoc.AlphaSquad.Controllador;

import com.duoc.AlphaSquad.Modelo.Comuna;
import com.duoc.AlphaSquad.Servicio.ComunaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comunas")
@CrossOrigin(origins = "*")
public class ComunaController {

    private final ComunaService service;

    public ComunaController(ComunaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Comuna> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comuna> obtener(@PathVariable Long id) {
        Comuna c = service.buscarPorId(id);
        return (c != null) ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Comuna crear(@RequestBody Comuna comuna) {
        return service.crear(comuna);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comuna> actualizar(@PathVariable Long id,
                                             @RequestBody Comuna comuna) {
        Comuna actualizado = service.actualizar(id, comuna);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
