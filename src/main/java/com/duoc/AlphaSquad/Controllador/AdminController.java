package com.duoc.AlphaSquad.Controllador;

import com.duoc.AlphaSquad.Modelo.Administrador;
import com.duoc.AlphaSquad.Servicio.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    @GetMapping
    public List<Administrador> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> obtener(@PathVariable Long id) {
        Administrador admin = service.buscarPorId(id);
        return (admin != null) ? ResponseEntity.ok(admin) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Administrador crear(@RequestBody Administrador admin) {
        return service.crear(admin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrador> actualizar(@PathVariable Long id,
                                                    @RequestBody Administrador admin) {
        Administrador actualizado = service.actualizar(id, admin);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
