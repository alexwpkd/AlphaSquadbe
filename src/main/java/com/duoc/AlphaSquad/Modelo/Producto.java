package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    @Column(nullable = false)
    private Integer precio;

    // Indica si el producto está disponible/en stock
    @Column(nullable = false)
    private boolean enStock;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false, length = 100)
    private String categoria;

    @Column(nullable = false, length = 100)
    private String subcategoria;

    // Aquí guardamos la URL o path de la imagen
    @Column(name = "imagen", length = 500)
    private String imagen;

    // ===== Constructores =====

    public Producto() {
    }

    // 🔹 Este constructor coincide EXACTO con tu DataInitializer:
    // new Producto(null, nombre, sku, precio, true, stock, descripcion, categoria, subcategoria, "products/product-img-1")
    public Producto(Long idProducto,
                    String nombre,
                    String sku,
                    Integer precio,
                    boolean enStock,
                    Integer stock,
                    String descripcion,
                    String categoria,
                    String subcategoria,
                    String imagen) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.sku = sku;
        this.precio = precio;
        this.enStock = enStock;
        this.stock = stock;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.imagen = imagen;
    }

    // ===== Getters y Setters =====

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public boolean isEnStock() {
        return enStock;
    }

    public void setEnStock(boolean enStock) {
        this.enStock = enStock;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
