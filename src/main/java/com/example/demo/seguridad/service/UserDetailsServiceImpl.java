package com.example.demo.seguridad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.seguridad.entity.Usuario;
import com.example.demo.seguridad.entity.UsuarioPrincipal;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UsuarioService userService;

	@Override
	public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
		Usuario usuario = userService.getByNombreUsuario(nombreUsuario).get();
		return UsuarioPrincipal.build(usuario);
	}

}
