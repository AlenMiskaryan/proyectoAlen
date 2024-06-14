package com.technology.sat.produccion.Class;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Usuario {
    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    @Email
    private String correoElectronico;


    @NotNull
    private String contrasena;

    @NotNull
    private LocalDate fechaNacimiento;

    @NotBlank
    private String empresa;

    @Enumerated(EnumType.STRING)
    private Rol rol;
}
