package com.technology.sat.produccion.Service;

import com.technology.sat.produccion.Class.Ticket;
import com.technology.sat.produccion.Class.Usuario;
import com.technology.sat.produccion.Repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> obtenerTodosLosTickets() {
        return ticketRepository.findAll();
    }

    public Ticket obtenerTicketPorId(Integer id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public Ticket guardarTicket(Ticket ticket) {
        if (ticket.getFechaCreacion() == null) {
            ticket.setFechaCreacion(LocalDateTime.now());
        }
        return ticketRepository.save(ticket);
    }

    public void eliminarTicket(Integer id) {
        ticketRepository.deleteById(id);
    }

    public Ticket actualizarTicket(Integer id, Ticket ticketActualizado) {
        Optional<Ticket> ticketExistente = ticketRepository.findById(id);
        if (ticketExistente.isPresent()) {
            Ticket ticket = ticketExistente.get();
            ticket.setTitulo(ticketActualizado.getTitulo());
            ticket.setDescripcion(ticketActualizado.getDescripcion());
            ticket.setUsuarioAsignado(ticketActualizado.getUsuarioAsignado());
            ticket.setRespuesta(ticketActualizado.getRespuesta());
            ticket.setFechaCreacion(ticketActualizado.getFechaCreacion() != null ? ticketActualizado.getFechaCreacion() : ticket.getFechaCreacion());
            ticket.setPrioridad(ticketActualizado.getPrioridad());
            ticket.setEstado(ticketActualizado.getEstado());
            return ticketRepository.save(ticket);
        } else {
            return null;
        }
    }

    public List<Ticket> obtenerTicketsPorUsuario(Usuario usuario) {
        return ticketRepository.findByUsuarioAsignado(usuario);
    }

    public List<Ticket> obtenerTodosLosTickets(Sort sort) {
        return ticketRepository.findAll(sort);
    }

    public List<Ticket> obtenerTicketsCreadosPorUsuario(Usuario usuario) {
        return ticketRepository.findByUsuarioCreador(usuario);
    }
}
