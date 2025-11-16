package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "arma_primaria")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArmaPrimaria extends Producto {

    private String calibre;
    private Double longitudCanon;
    private String material;
    private String tipoDisparo;
}
