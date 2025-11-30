package com.duoc.AlphaSquad.Controllador;

import com.duoc.AlphaSquad.Modelo.Region;
import com.duoc.AlphaSquad.Servicio.RegionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regiones")
// @CrossOrigin("*")
public class RegionController {

    private final RegionService service;

    public RegionController(RegionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Region> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> obtener(@PathVariable Long id) {
        Region r = service.buscarPorId(id);
        return (r != null) ? ResponseEntity.ok(r) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Region crear(@RequestBody Region region) {
        return service.crear(region);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> actualizar(@PathVariable Long id,
                                             @RequestBody Region region) {
        Region actualizado = service.actualizar(id, region);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
