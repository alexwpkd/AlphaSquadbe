package com.duoc.AlphaSquad.Controllador;

import com.duoc.AlphaSquad.Modelo.Envio;
import com.duoc.AlphaSquad.Servicio.EnvioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/envios")
// @CrossOrigin("*")
public class EnvioController {

    private final EnvioService service;

    public EnvioController(EnvioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Envio> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> obtener(@PathVariable Long id) {
        Envio envio = service.buscarPorId(id);
        return (envio != null) ? ResponseEntity.ok(envio) : ResponseEntity.notFound().build();
    }

    @GetMapping("/venta/{idVenta}")
    public ResponseEntity<Envio> obtenerPorVenta(@PathVariable Long idVenta) {
        return service.buscarPorVenta(idVenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Envio crear(@RequestBody Envio envio) {
        return service.crear(envio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizar(@PathVariable Long id,
                                            @RequestBody Envio envio) {
        Envio actualizado = service.actualizar(id, envio);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
