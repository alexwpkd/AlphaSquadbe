package com.duoc.AlphaSquad.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include  // Solo el id entra en equals/hashCode
    private Long idProducto;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private Integer precio;

    @Column(nullable = false)
    private Boolean enStock;

    @Column(nullable = false)
    private Integer stock;

    private String imagen;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private String subcategoria;

    public Producto(Long idProducto, String nombre, String sku, Integer precio,
                    Boolean enStock, Integer stock, String descripcion,
                    String categoria, String subcategoria, String imagen) {
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

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<DetalleVenta> ventas = new HashSet<>();

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<DetalleCompra> compras = new HashSet<>();
}
