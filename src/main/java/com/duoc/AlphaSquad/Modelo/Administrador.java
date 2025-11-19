package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "administrador")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdministrador;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String rut;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
    private Set<Empleado> empleados = new HashSet<>();;

    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
    private Set<Compra> compras = new HashSet<>();

}
