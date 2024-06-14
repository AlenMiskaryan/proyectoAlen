package com.technology.sat.produccion.Controller;

import com.technology.sat.produccion.Class.Ticket;
import com.technology.sat.produccion.Class.Usuario;
import com.technology.sat.produccion.Service.TicketService;
import com.technology.sat.produccion.Service.UsuarioService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TicketService ticketService;

    // Gestionar Usuarios

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.obtenerTodosLosUsuarios());
        model.addAttribute("page", "usuarios");
        return "admin_panel";
    }

    @GetMapping("/usuarios/nuevo")
    public String mostrarFormularioDeCrearUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("page", "crear_usuario");
        return "admin_panel";
    }

    @PostMapping("/usuarios")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuarioService.guardarUsuario(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormularioDeEditarUsuario(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("page", "editar_usuario");
        return "admin_panel";
    }

    @PostMapping("/usuarios/{id}")
    public String actualizarUsuario(@PathVariable Integer id, @ModelAttribute("usuario") Usuario usuario) {
        usuarioService.actualizarUsuario(id, usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/admin/usuarios";
    }

    // Gestionar Tickets

    @GetMapping("/tickets")
    public String listarTickets(Model model) {
        Usuario usuarioFijo = usuarioService.obtenerUsuarioPorId(1);  
        List<Ticket> tickets = ticketService.obtenerTodosLosTickets();
        
        if (usuarioFijo != null) {
            tickets.forEach(ticket -> {
                ticket.setUsuarioCreador(usuarioFijo);  
               
                System.out.println("Ticket ID: " + ticket.getId() + ", Creator: " + (ticket.getUsuarioCreador() != null ? ticket.getUsuarioCreador().getNombre() : "null"));
            });
        } else {
            System.out.println("mensaje de error.");
        }
    
        model.addAttribute("tickets", tickets);
        model.addAttribute("page", "tickets");
        return "admin_panel";
    }
    

    @GetMapping("/tickets/nuevo")
    public String mostrarFormularioDeCrearTicket(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("usuarios", usuarioService.obtenerTodosLosUsuarios());
        model.addAttribute("page", "crear_ticket");
        return "admin_panel";
    }

    @PostMapping("/tickets")
    public String guardarTicket(@ModelAttribute("ticket") Ticket ticket) {
        if (ticket.getFechaCreacion() == null) {
            ticket.setFechaCreacion(LocalDateTime.now());  // Establece la fecha de creación si está null
        }
        ticketService.guardarTicket(ticket);
        return "redirect:/admin/tickets";
    }

 

    @GetMapping("/tickets/editar/{id}")
    public String mostrarFormularioDeEditarTicket(@PathVariable Integer id, Model model) {
        Ticket ticket = ticketService.obtenerTicketPorId(id);
        model.addAttribute("ticket", ticket);
        model.addAttribute("usuarios", usuarioService.obtenerTodosLosUsuarios());
        model.addAttribute("page", "editar_ticket");
        return "admin_panel";
    }

    @PostMapping("/tickets/{id}")
    public String actualizarTicket(@PathVariable Integer id, @ModelAttribute("ticket") Ticket ticket) {
        ticketService.actualizarTicket(id, ticket);
        return "redirect:/admin/tickets";
    }

    @GetMapping("/tickets/eliminar/{id}")
    public String eliminarTicket(@PathVariable Integer id) {
        ticketService.eliminarTicket(id);
        return "redirect:/admin/tickets";
    }

    @PostMapping("/tickets/asignar")
    public String asignarTicket(@RequestParam Integer ticketId, @RequestParam Integer usuarioId) {
        Ticket ticket = ticketService.obtenerTicketPorId(ticketId);
        Usuario usuario = usuarioService.obtenerUsuarioPorId(usuarioId);
        if (ticket != null && usuario != null) {
            ticket.setUsuarioAsignado(usuario);
            ticketService.actualizarTicket(ticketId, ticket);
        }
        return "redirect:/admin/tickets";
    }
}
