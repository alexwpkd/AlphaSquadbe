package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String rut;

    //Tengo pensado borrar correo y contraseña ya que solo generaremos vista de admin cliente
    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String password;

    // // TOKEN JWT (se implementará más adelante)
    // private String tokenJwt;

    @ManyToOne
    @JoinColumn(name = "administrador_id")
    private Administrador administrador;

}
