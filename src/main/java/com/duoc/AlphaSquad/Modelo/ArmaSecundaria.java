package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "arma_secundaria")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArmaSecundaria extends Producto {

    private String calibre;
    private String tipoDisparo;
    private Double peso;
}
