package com.technology.sat.produccion.Controller;

import com.technology.sat.produccion.Class.Estado;
import com.technology.sat.produccion.Class.Prioridad;
import com.technology.sat.produccion.Class.Ticket;
import com.technology.sat.produccion.Class.Usuario;
import com.technology.sat.produccion.Service.TicketService;
import com.technology.sat.produccion.Service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/cliente/tickets")
public class ClienteController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public String listarTickets(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(userDetails.getUsername());
        List<Ticket> tickets = ticketService.obtenerTicketsCreadosPorUsuario(usuario);
        List<Usuario> soportes = usuarioService.obtenerUsuariosDeSoporte();
        model.addAttribute("tickets", tickets);
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("soportes", soportes);
        return "cliente_panel";
    }

    @PostMapping("/crear")
    public String crearTicket(@ModelAttribute Ticket ticket,
                              @RequestParam("usuarioAsignado.id") Integer usuarioAsignadoId,
                              @RequestParam("prioridad") Prioridad prioridad,
                              @RequestParam("estado") Estado estado,
                              @AuthenticationPrincipal UserDetails userDetails,
                              Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(userDetails.getUsername());
        Usuario soporte = usuarioService.obtenerUsuarioPorId(usuarioAsignadoId);

        if (soporte != null) {
            ticket.setUsuarioAsignado(soporte);
            ticket.setFechaCreacion(LocalDateTime.now());
            ticket.setPrioridad(prioridad);
            ticket.setEstado(estado);
            ticket.setUsuarioCreador(usuario);
            ticketService.guardarTicket(ticket);
            return "redirect:/cliente/tickets/listar";
        }

        model.addAttribute("error", "No se pudo asignar soporte o guardar el ticket.");
        List<Usuario> soportes = usuarioService.obtenerUsuariosDeSoporte();
        model.addAttribute("soportes", soportes);
        return "cliente_panel";
    }



    @PostMapping("/eliminar/{id}")
    public String eliminarTicket(@PathVariable Integer id) {
        ticketService.eliminarTicket(id);
        return "redirect:/cliente/tickets/listar";
    }
}
