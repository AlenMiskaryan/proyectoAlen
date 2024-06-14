package com.technology.sat.produccion.Class;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Ticket {
    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    private String titulo;
    @NotBlank
    private String descripcion;

    @ManyToOne
    private Usuario usuarioAsignado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_creador_id")
    private Usuario usuarioCreador;

    private String respuesta;

    private LocalDateTime fechaCreacion;

    @Enumerated
    private Prioridad prioridad;

    @Enumerated
    private Estado estado;

}
