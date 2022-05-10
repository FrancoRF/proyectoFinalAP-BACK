package com.example.demo.seguridad.controller;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Mensaje;
import com.example.demo.seguridad.dto.JwtDto;
import com.example.demo.seguridad.dto.LoginUsuario;
import com.example.demo.seguridad.dto.NuevoUsuario;
import com.example.demo.seguridad.entity.Rol;
import com.example.demo.seguridad.entity.Usuario;
import com.example.demo.seguridad.enums.RolNombre;
import com.example.demo.seguridad.jwt.JwtProvider;
import com.example.demo.seguridad.service.RolService;
import com.example.demo.seguridad.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	PasswordEncoder contraseñaCodificada;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	UsuarioService userService;
	
	@Autowired
	RolService rolService;
	
	@Autowired
	JwtProvider jwtProvider;
	
	
	@PostMapping("/crear")
	public ResponseEntity<?> crear(@RequestBody NuevoUsuario nuevoUsuario, BindingResult binResult){
		if(binResult.hasErrors())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campos mal puestos o email invalido");
		if(userService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ese nombre ya existe");
		if(userService.existsByEmail(nuevoUsuario.getEmail()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ese email ya existe");
		Usuario usuario =
				new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(), 
						contraseñaCodificada.encode(nuevoUsuario.getPassword()));
		Set<Rol> roles = new HashSet<>();
		roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
		if(nuevoUsuario.getRoles().contains("admin"))
			roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
		usuario.setRoles(roles);
		userService.guardar(usuario);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("usuario creado");
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtDto> login(@RequestBody LoginUsuario loginUsuario, BindingResult binResult){
		if(binResult.hasErrors())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Authentication auth = 
				authManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = jwtProvider.generarToken(auth);
		JwtDto jwtDto = new JwtDto(jwt);
		return new ResponseEntity<>(jwtDto, HttpStatus.OK);
	}
	
	@PostMapping("/refrescar")
	public ResponseEntity<JwtDto> refrescar(@RequestBody JwtDto jwtDto) throws ParseException{
		String token = jwtProvider.refreshToken(jwtDto);
		JwtDto jwt = new JwtDto(token);
		return new ResponseEntity<>(jwt, HttpStatus.OK);
	}
	
	@DeleteMapping("/eliminar/{nombreUsuario}")
	public ResponseEntity<?> eliminar(@PathVariable("nombreUsuario") String nombreUsuario){
		if(!userService.existsByNombreUsuario(nombreUsuario))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe el usuario");
		Usuario user = userService.getByNombre(nombreUsuario);
		userService.eliminar(user);
		if(user.getNombre().length() != 0) {
			return new ResponseEntity<>(new Mensaje("No se elimino el usuario"), HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(new Mensaje("Usuario Eliminado con exito"), HttpStatus.OK);
		}
	}
	

}
