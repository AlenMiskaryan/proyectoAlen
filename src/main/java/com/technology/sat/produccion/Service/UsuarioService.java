package com.technology.sat.produccion.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.technology.sat.produccion.Class.Rol;
import com.technology.sat.produccion.Class.Usuario;
import com.technology.sat.produccion.Repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario guardarUsuario(Usuario usuario) {
        if (usuarioRepositorio.findByCorreoElectronico(usuario.getCorreoElectronico()) != null) {
            throw new RuntimeException("El correo electrónico ya está en uso.");
        }
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepositorio.save(usuario);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public Usuario obtenerUsuarioPorId(Integer id) {
        return usuarioRepositorio.findById(id).orElse(null);  // Devuelve directamente el Usuario o null
    }

    public Usuario actualizarUsuario(Integer id, Usuario usuario) {
        if (usuarioRepositorio.existsById(id)) {
            usuario.setId(id);
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            return usuarioRepositorio.save(usuario);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    public void eliminarUsuario(Integer id) {
        usuarioRepositorio.deleteById(id);
    }

    public Usuario obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreoElectronico(correo); 
    }

    public List<Usuario> obtenerUsuariosPorRol(Rol rol) {
        return usuarioRepositorio.findByRol(rol);
    }

    public List<Usuario> obtenerUsuariosDeSoporte() {
        return usuarioRepositorio.findByRol(Rol.SOPORTE);
    }

    
}
