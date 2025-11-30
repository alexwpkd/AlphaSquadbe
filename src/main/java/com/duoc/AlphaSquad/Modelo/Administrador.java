package com.duoc.AlphaSquad.Modelo;

import com.duoc.AlphaSquad.Validacion.RutValido;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

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
    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$",
            message = "El nombre solo puede contener letras y espacios")
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message = "El apellido es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$",
            message = "El apellido solo puede contener letras y espacios")
    private String apellido;

    @Column(nullable = false)
    @NotBlank(message = "El correo es obligatorio")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@alpha\\.cl$",
            message = "El correo debe pertenecer al dominio @alpha.cl"
    )
    private String correo;


    @NotBlank(message = "El RUT es obligatorio")
    @RutValido(message = "El RUT no es válido")
    private String rut;

    @Column(nullable = false)
    private String password;

}
