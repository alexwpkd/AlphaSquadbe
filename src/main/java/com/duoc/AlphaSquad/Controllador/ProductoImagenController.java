package com.duoc.AlphaSquad.Controllador;

import com.duoc.AlphaSquad.Modelo.Producto;
import com.duoc.AlphaSquad.Servicio.ProductoImagenService;
import com.duoc.AlphaSquad.dto.ProductoCreateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
// @CrossOrigin("*")
public class ProductoImagenController {

    private final ProductoImagenService service;
    private final ObjectMapper objectMapper;

    public ProductoImagenController(ProductoImagenService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> crear(
            @RequestParam("dto") String dtoJson,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen
    ) throws JsonProcessingException {

        ProductoCreateDTO dto = objectMapper.readValue(dtoJson, ProductoCreateDTO.class);

        Producto creado = service.crearConDTO(dto, imagen);

        return ResponseEntity
                .created(URI.create("/api/productos/" + creado.getIdProducto()))
                .body(creado);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @RequestParam("dto") String dtoJson,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen
    ) throws JsonProcessingException {

        ProductoCreateDTO dto = objectMapper.readValue(dtoJson, ProductoCreateDTO.class);

        Producto actualizado = service.actualizarConDTO(id, dto, imagen);

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
