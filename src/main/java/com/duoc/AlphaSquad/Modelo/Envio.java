package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "envio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnvio;

    @Column(nullable = false)
    private String direccionEntrega;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private Integer costoEnvio;

    @Column(nullable = false)
    private String estado; // pendiente, en_transito, entregado

    private LocalDate fechaEntregaEstimada;

    @OneToOne
    @JoinColumn(name = "venta_id", nullable = false, unique = true)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @ManyToOne()
    @JoinColumn(name = "comuna_id")
    private Comuna comuna;
}
