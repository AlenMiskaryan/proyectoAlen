package com.technology.sat.produccion.Controller;

import com.technology.sat.produccion.Class.Ticket;
import com.technology.sat.produccion.Service.TicketService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/soporte")
public class SoporteController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets")
    public String listaTickets(Model model, @RequestParam(name = "sort", required = false) String sort) {
        List<Ticket> tickets;
        if ("prioridad".equals(sort)) {
            tickets = ticketService.obtenerTodosLosTickets(Sort.by(Sort.Direction.DESC, "prioridad"));
        } else {
            tickets = ticketService.obtenerTodosLosTickets();
        }
        model.addAttribute("tickets", tickets);
        return "panel_soporte";
    }

    @PostMapping("/tickets/{id}/actualizar")
    public String actualizarTicket(@PathVariable Integer id, @ModelAttribute Ticket ticketActualizado) {
        Ticket ticket = ticketService.obtenerTicketPorId(id);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket no válido: " + id);
        }
        ticket.setDescripcion(ticketActualizado.getDescripcion());
        ticket.setEstado(ticketActualizado.getEstado());
        ticket.setPrioridad(ticketActualizado.getPrioridad());
        ticketService.actualizarTicket(ticket.getId(), ticket);
        return "redirect:/soporte/tickets";
    }
}
