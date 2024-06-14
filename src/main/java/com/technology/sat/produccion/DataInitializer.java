package com.technology.sat.produccion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.technology.sat.produccion.Class.Usuario;
import com.technology.sat.produccion.Class.Rol;
import com.technology.sat.produccion.Service.UsuarioService;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
        Usuario admin = new Usuario();
        admin.setNombre("Alen");
        admin.setApellido("Miskaryan");
        admin.setFechaNacimiento(LocalDate.of(2002, 4, 26));
        admin.setCorreoElectronico("admin@sattechnology.com");
        admin.setContrasena("Alen2002");
        admin.setEmpresa("SAT Technology");
        admin.setRol(Rol.ADMINISTRADOR);
        usuarioService.guardarUsuario(admin);

        Usuario soporte1 = new Usuario();
        soporte1.setNombre("Carlos");
        soporte1.setApellido("Perez");
        soporte1.setCorreoElectronico("soporte@sattechnology.com");
        soporte1.setContrasena("Soporte123");
        soporte1.setFechaNacimiento(LocalDate.of(1995, 8, 15));
        soporte1.setEmpresa("SAT Technology");
        soporte1.setRol(Rol.SOPORTE);
        usuarioService.guardarUsuario(soporte1);

        Usuario soporte2 = new Usuario();
        soporte2.setNombre("Ana");
        soporte2.setApellido("Lopez");
        soporte2.setCorreoElectronico("ana.lopez@sattechnology.com");
        soporte2.setContrasena("Ana12345");
        soporte2.setFechaNacimiento(LocalDate.of(1988, 3, 22));
        soporte2.setEmpresa("SAT Technology");
        soporte2.setRol(Rol.SOPORTE);
        usuarioService.guardarUsuario(soporte2);

        Usuario soporte3 = new Usuario();
        soporte3.setNombre("Miguel");
        soporte3.setApellido("Martinez");
        soporte3.setCorreoElectronico("miguel.martinez@sattechnology.com");
        soporte3.setContrasena("Miguel123");
        soporte3.setFechaNacimiento(LocalDate.of(1992, 11, 10));
        soporte3.setEmpresa("SAT Technology");
        soporte3.setRol(Rol.SOPORTE);
        usuarioService.guardarUsuario(soporte3);

        Usuario soporte4 = new Usuario();
        soporte4.setNombre("Lucia");
        soporte4.setApellido("Garcia");
        soporte4.setCorreoElectronico("lucia.garcia@sattechnology.com");
        soporte4.setContrasena("Lucia123");
        soporte4.setFechaNacimiento(LocalDate.of(1997, 7, 30));
        soporte4.setEmpresa("SAT Technology");
        soporte4.setRol(Rol.SOPORTE);
        usuarioService.guardarUsuario(soporte4);

        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Gomez");
        usuario.setCorreoElectronico("usuario@sattechnology.com");
        usuario.setContrasena("Usuario123");
        usuario.setFechaNacimiento(LocalDate.of(1990, 5, 20));
        usuario.setEmpresa("SAT Technology");
        usuario.setRol(Rol.USUARIO);
        usuarioService.guardarUsuario(usuario);
    }
}
