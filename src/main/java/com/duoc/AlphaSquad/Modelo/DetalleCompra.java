package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_compra")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompraItem;

    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
}
