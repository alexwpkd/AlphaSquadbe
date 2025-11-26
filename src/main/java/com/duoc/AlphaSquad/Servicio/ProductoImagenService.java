package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.Producto;
import com.duoc.AlphaSquad.Repositorio.RepProducto;
import com.duoc.AlphaSquad.exception.FileStorageException;
import com.duoc.AlphaSquad.dto.ProductoCreateDTO;
import com.duoc.AlphaSquad.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductoImagenService {

    private final RepProducto repo;
    private final FileStorageService fileStorage;

    public ProductoImagenService(RepProducto repo, FileStorageService fileStorage) {
        this.repo = repo;
        this.fileStorage = fileStorage;
    }

    public Producto crear(String nombre, int precio, MultipartFile imagen) {
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setPrecio(precio);

        if (imagen != null && !imagen.isEmpty()) {
            String file = fileStorage.storeFile(imagen);
            p.setImagen(file);
        }

        return repo.save(p);
    }

    public List<Producto> listar() {
        return repo.findAll();
    }

    public Producto buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Producto no encontrado: " + id)
        );
    }

    public Producto actualizar(Long id, String nombre, int precio, MultipartFile imagen) {
        Producto p = buscarPorId(id);

        p.setNombre(nombre);
        p.setPrecio(precio);

        if (imagen != null && !imagen.isEmpty()) {
            if (p.getImagen() != null) fileStorage.deleteFile(p.getImagen());
            String nuevo = fileStorage.storeFile(imagen);
            p.setImagen(nuevo);
        }

        return repo.save(p);
    }

    public void eliminar(Long id) {
        Producto p = buscarPorId(id);
        if (p.getImagen() != null) fileStorage.deleteFile(p.getImagen());
        repo.delete(p);
    }

    public Producto crearConDTO(ProductoCreateDTO dto, MultipartFile imagen) {
        Producto p = new Producto();

        p.setNombre(dto.getNombre());
        p.setPrecio(dto.getPrecio());
        p.setSku(dto.getSku());
        p.setStock(dto.getStock());
        p.setEnStock(dto.getStock() > 0);
        p.setCategoria(dto.getCategoria());
        p.setSubcategoria(dto.getSubcategoria());
        p.setDescripcion(dto.getDescripcion());

        if (imagen != null && !imagen.isEmpty()) {
            p.setImagen(fileStorage.storeFile(imagen));
        }

        return repo.save(p);
    }

    public Producto actualizarConDTO(Long id, ProductoCreateDTO dto, MultipartFile imagen) {
        Producto p = buscarPorId(id);

        p.setNombre(dto.getNombre());
        p.setPrecio(dto.getPrecio());
        p.setSku(dto.getSku());
        p.setStock(dto.getStock());
        p.setEnStock(dto.getStock() > 0);
        p.setCategoria(dto.getCategoria());
        p.setSubcategoria(dto.getSubcategoria());
        p.setDescripcion(dto.getDescripcion());

        if (imagen != null && !imagen.isEmpty()) {
            if (p.getImagen() != null) fileStorage.deleteFile(p.getImagen());
            p.setImagen(fileStorage.storeFile(imagen));
        }

        return repo.save(p);
    }

}