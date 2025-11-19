package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false, unique = true)
    private String rut;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "comuna_id")
    private Comuna comuna;

    private String direccion;

    @OneToMany(mappedBy = "cliente")
    private Set<Venta> ventas = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    private Set<Envio> envios = new HashSet<>();
}
