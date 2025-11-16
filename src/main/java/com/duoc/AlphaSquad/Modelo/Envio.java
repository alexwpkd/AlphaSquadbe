package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "envio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnvio;

    private String direccionEntrega;

    private String ciudad;

    private String region;

    private Integer costoEnvio;

    private String estado;

    @OneToOne
    @JoinColumn(name = "venta_id", unique = true)
    private Venta venta;
}
