package com.example.demo.security.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.security.entity.Usuario;
import com.example.demo.security.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {
	
	@Autowired
	UsuarioRepository userRepository;
	
	public List<Usuario> listaUsuario(){
		return userRepository.findAll();
	}
	
	public Usuario getByNombre(String nombreUsuario) {
		return userRepository.findByNombre(nombreUsuario);
	}
	
	public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
		return userRepository.findByNombreUsuario(nombreUsuario);
	}
	
	public boolean existsByNombreUsuario(String nombreUsuario) {
		return userRepository.existsByNombreUsuario(nombreUsuario);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public void guardar(Usuario usuario) {
		userRepository.save(usuario);
	}
	
	public void eliminar(Usuario usuario) {
		userRepository.delete(usuario);;
	}

}
