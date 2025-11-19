package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comuna")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComuna;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
}
