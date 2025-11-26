package com.duoc.AlphaSquad.Controllador;

import com.duoc.AlphaSquad.Servicio.ProductoImagenService;
import com.duoc.AlphaSquad.dto.ProductoCreateDTO;
import com.duoc.AlphaSquad.Modelo.Producto;
import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin("*")
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
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> crear(
            @Valid @ModelAttribute ProductoCreateDTO dto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) {
        Producto creado = service.crearConDTO(dto, imagen);
        return ResponseEntity.created(URI.create("/api/productos/" + creado.getIdProducto()))
                .body(creado);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute ProductoCreateDTO dto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) {
        return ResponseEntity.ok(service.actualizarConDTO(id, dto, imagen));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
