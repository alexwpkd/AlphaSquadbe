package com.duoc.AlphaSquad.Modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accesorio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accesorio extends Producto {

    private String especificacion;
}
