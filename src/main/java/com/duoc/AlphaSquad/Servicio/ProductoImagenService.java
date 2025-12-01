package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.Producto;
import com.duoc.AlphaSquad.Repositorio.RepProducto;
import com.duoc.AlphaSquad.dto.ProductoCreateDTO;
import com.duoc.AlphaSquad.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoImagenService {

    private final RepProducto repo;

    public ProductoImagenService(RepProducto repo) {
        this.repo = repo;
    }

    // ===== Consultas básicas =====

    public List<Producto> listar() {
        return repo.findAll();
    }

    public Producto buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Producto no encontrado: " + id)
        );
    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return repo.findByCategoria(categoria);
    }

    public List<Producto> buscarPorSubcategoria(String subcategoria) {
        return repo.findBySubcategoria(subcategoria);
    }

    // ===== Crear / actualizar usando DTO con URL =====

    public Producto crearConDTO(ProductoCreateDTO dto) {
        Producto p = new Producto();
        mapearDesdeDTO(dto, p);
        return repo.save(p);
    }

    public Producto actualizarConDTO(Long id, ProductoCreateDTO dto) {
        Producto p = buscarPorId(id);
        mapearDesdeDTO(dto, p);
        return repo.save(p);
    }

    public void eliminar(Long id) {
        Producto p = buscarPorId(id);
        repo.delete(p);
    }

    // ===== Mapeo DTO -> Entidad =====

    private void mapearDesdeDTO(ProductoCreateDTO dto, Producto p) {
        p.setNombre(dto.getNombre());
        p.setPrecio(dto.getPrecio());
        p.setSku(dto.getSku());
        p.setStock(dto.getStock());

        // enStock se calcula en base al stock
        p.setEnStock(dto.getStock() != null && dto.getStock() > 0);

        p.setCategoria(dto.getCategoria());
        p.setSubcategoria(dto.getSubcategoria());
        p.setDescripcion(dto.getDescripcion());

        // Aquí guardamos la URL que el admin envía
        p.setImagen(dto.getImagenUrl());
    }
}
