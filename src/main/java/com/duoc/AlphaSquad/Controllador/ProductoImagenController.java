package com.duoc.AlphaSquad.Controllador;

import com.duoc.AlphaSquad.Modelo.Producto;
import com.duoc.AlphaSquad.Servicio.ProductoImagenService;
import com.duoc.AlphaSquad.dto.ProductoCreateDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
// @CrossOrigin("*")
public class ProductoImagenController {

    private final ProductoImagenService service;

    public ProductoImagenController(ProductoImagenService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        Producto producto = service.buscarPorId(id);
        return (producto != null)
                ? ResponseEntity.ok(producto)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/categoria/{categoria}")
    public List<Producto> buscarPorCategoria(@PathVariable String categoria) {
        return service.buscarPorCategoria(categoria);
    }

    @GetMapping("/subcategoria/{subcategoria}")
    public List<Producto> buscarPorSubcategoria(@PathVariable String subcategoria) {
        return service.buscarPorSubcategoria(subcategoria);
    }

    // ===== Crear / actualizar con JSON normal =====

    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody ProductoCreateDTO dto) {

        Producto creado = service.crearConDTO(dto);

        return ResponseEntity
                .created(URI.create("/api/productos/" + creado.getIdProducto()))
                .body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id,
                                               @Valid @RequestBody ProductoCreateDTO dto) {

        Producto actualizado = service.actualizarConDTO(id, dto);

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
