package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "region")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegion;

    @Column(nullable = false, unique = true)
    private String nombre;
}
