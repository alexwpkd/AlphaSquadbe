package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "compra")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompra;

    private Integer totalCompra;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "administrador_id")
    private Administrador administrador;
}
