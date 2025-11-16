package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "carrito_de_compras")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoDeCompras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrito;

    private LocalDateTime fechaCreacion; //Tengo duda si dejamos la fecha de creacion

    @Column(nullable = false)
    private String estado; // para pasar de pendiente,realizado, cancelado

    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false, unique = true)
    private Cliente cliente;
}
