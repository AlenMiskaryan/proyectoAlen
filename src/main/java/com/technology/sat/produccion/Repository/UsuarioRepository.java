package com.technology.sat.produccion.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technology.sat.produccion.Class.Rol;
import com.technology.sat.produccion.Class.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByCorreoElectronico(String correoElectronico);
    List<Usuario> findByRol(Rol rol);

}
