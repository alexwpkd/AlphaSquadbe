package com.duoc.AlphaSquad.Controllador;

import com.duoc.AlphaSquad.Modelo.Venta;
import com.duoc.AlphaSquad.Servicio.VentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    private final VentaService service;

    public VentaController(VentaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Venta> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtener(@PathVariable Long id) {
        Venta v = service.buscarPorId(id);
        return (v != null) ? ResponseEntity.ok(v) : ResponseEntity.notFound().build();
    }

    @GetMapping("/cliente/{idCliente}")
    public List<Venta> porCliente(@PathVariable Long idCliente) {
        return service.buscarPorCliente(idCliente);
    }

    @GetMapping("/empleado/{idEmpleado}")
    public List<Venta> porEmpleado(@PathVariable Long idEmpleado) {
        return service.buscarPorEmpleado(idEmpleado);
    }

    @PostMapping
    public Venta crear(@RequestBody Venta venta) {
        return service.crear(venta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizar(@PathVariable Long id,
                                            @RequestBody Venta venta) {
        Venta actualizado = service.actualizar(id, venta);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
