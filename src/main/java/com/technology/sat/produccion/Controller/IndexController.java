package com.technology.sat.produccion.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.technology.sat.produccion.Class.Incidencia;
import com.technology.sat.produccion.Class.Usuario;
import com.technology.sat.produccion.Service.IncidenciaService;
import com.technology.sat.produccion.Service.UsuarioService;

@Controller
public class IndexController {

    @Autowired
    private IncidenciaService incidenciaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String urlIndex() {
        return "index";
    }

    @GetMapping("/incidencia")
    public String mostrarFormularioIncidencia(Model model) {
        model.addAttribute("incidencia", new Incidencia());
        return "incidencia";
    }

    @PostMapping("/incidencia")
    public String enviarIncidencia(@ModelAttribute Incidencia incidencia) {
        incidenciaService.procesarIncidencia(incidencia);
        return "resultado";
    }

    @GetMapping("/register")
    public String enviarFormularioDeRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String cargarFormulario(@ModelAttribute Usuario usuario) {
        try {
            usuarioService.guardarUsuario(usuario);
            return "redirect:/signin";
        } catch (RuntimeException e) {
            return "register";
        }
    }

    @GetMapping("/ia")
    public String abrirIA() {
        return "ia";
    }

  
    @GetMapping("/signin")
    public String showLogin(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("loginError", "Correo electrónico o contraseña incorrectos.");
        }
        return "signin";
    }

    @GetMapping("/signout")
    public String logout() {
        return "redirect:/signin";
    }
}
