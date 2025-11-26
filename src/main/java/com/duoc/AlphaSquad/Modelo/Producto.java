package com.duoc.AlphaSquad.Modelo;

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

    @OneToMany(mappedBy = "producto")
    private Set<DetalleVenta> ventas = new HashSet<>();;

    @OneToMany(mappedBy = "producto")
    private Set<DetalleCompra> compras = new HashSet<>();;
}
