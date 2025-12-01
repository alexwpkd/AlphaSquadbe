package com.duoc.AlphaSquad.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long idCompradetalle;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Integer precioUnitario;

    @Column(nullable = false)
    private Integer subtotal;

    private final double iva = 0.19;

    @ManyToOne
    @JoinColumn(name = "compra_id", nullable = false)
    @JsonIgnore               // 👈 EVITA ciclos Compra ↔ DetalleCompra
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
}
