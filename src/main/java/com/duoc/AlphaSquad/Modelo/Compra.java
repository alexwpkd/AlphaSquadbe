package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "compra")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompra;

    @Column(nullable = false)
    private Integer totalCompra;

    @Column(nullable = false)
    private String estado; // recibida, pendiente

    @Column(nullable = false)
    private LocalDateTime fechaCompra;

    @ManyToOne
    @JoinColumn(name = "administrador_id")
    private Administrador administrador;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private Set<DetalleCompra> detalleC = new HashSet<>();
}
