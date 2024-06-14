package com.technology.sat.produccion.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technology.sat.produccion.Class.Ticket;
import com.technology.sat.produccion.Class.Usuario;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByUsuarioAsignado_Id(Integer usuarioId);

    List<Ticket> findByUsuarioAsignado(Usuario usuarioAsignado);

    List<Ticket> findByUsuarioCreador(Usuario usuarioCreador);



}
