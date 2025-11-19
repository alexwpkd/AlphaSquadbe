package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleProducto;

    @OneToOne
    @JoinColumn(name = "producto_id", nullable = false, unique = true)
    private Producto producto;

    // se debe agregar como los detalles de cada arma no me acuerdo si lo tenemos en el sitio web
}