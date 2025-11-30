package com.duoc.AlphaSquad.Modelo;

import com.duoc.AlphaSquad.Validacion.RutValido;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$",
            message = "El nombre solo puede contener letras y espacios")
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message = "El apellido es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$",
            message = "El apellido solo puede contener letras y espacios")
    private String apellidos;

    @Column(nullable = false, unique = true)

    @NotBlank(message = "El RUT es obligatorio")
    @RutValido(message = "El RUT no es válido")
    private String rut;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido")
    private String correo;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "comuna_id")
    private Comuna comuna;

    @Column(nullable = false)
    private String direccion;
}
