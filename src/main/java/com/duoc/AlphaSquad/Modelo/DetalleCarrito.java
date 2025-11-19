package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle-carrito")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarritoDetalle;

    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "carrito_id", nullable = false)
    private CarritoCompras carrito;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
}
