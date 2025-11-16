package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "municion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Municion extends Producto {

    private String calibre;
    private Integer cantidadPorPaquete;
}
