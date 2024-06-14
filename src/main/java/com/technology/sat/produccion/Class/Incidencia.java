package com.technology.sat.produccion.Class;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Incidencia {
    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String apellido;
    private Integer telefono;
    private String empresa;
    private String email;
    private String mensaje;
}
