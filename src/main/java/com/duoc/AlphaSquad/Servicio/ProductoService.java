package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.Producto;
import com.duoc.AlphaSquad.Repositorio.RepProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private RepProducto repProducto;

    public List<Producto> listar() {
        return repProducto.findAll();
    }

    public Producto buscarPorId(Long id) {
        return repProducto.findById(id).orElse(null);
    }

    public Producto crear(Producto producto) {
        return repProducto.save(producto);
    }

    public Producto actualizar(Long id, Producto producto) {
        Producto existente = buscarPorId(id);
        if (existente != null) {

            existente.setNombre(producto.getNombre());
            existente.setSku(producto.getSku());
            existente.setPrecio(producto.getPrecio());
            existente.setEnStock(producto.getEnStock());
            existente.setStock(producto.getStock());
            existente.setDescripcion(producto.getDescripcion());
            existente.setCategoria(producto.getCategoria());
            existente.setSubcategoria(producto.getSubcategoria());

            return repProducto.save(existente);
        }
        return null;
    }

    public Optional<Producto> buscarPorSku(String sku) {
        return repProducto.findBySku(sku);
    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return repProducto.findByCategoria(categoria);
    }

    public List<Producto> buscarPorSubcategoria(String subcategoria) {
        return repProducto.findBySubcategoria(subcategoria);
    }

    public void eliminar(Long id) {
        repProducto.deleteById(id);
    }
}
