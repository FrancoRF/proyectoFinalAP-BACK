package com.example.demo.seguridad.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.seguridad.entity.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer>{
	
	Optional<Usuario> findByNombreUsuario(String nombreUsuario);
	Usuario findByNombre(String nombreUsuario);
	boolean existsByNombreUsuario(String nombreUsuario);
	boolean existsByEmail(String email);

}
