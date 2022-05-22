package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Mensaje;
import com.example.demo.entity.Educacion;
import com.example.demo.security.entity.Usuario;
import com.example.demo.security.service.UsuarioService;
import com.example.demo.service.EducacionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/auth/educaciones")
@CrossOrigin(origins = "*")
public class EducacionController {
	
	@Autowired
	EducacionService educacionService;
	
	@Autowired
	UsuarioService usuarioService;

	@GetMapping("/")
	public ResponseEntity<List<Educacion>> listEducacion(){
		List<Educacion> list = educacionService.list();
		return new ResponseEntity<List<Educacion>>(list, HttpStatus.OK); 
	}
	
	@PostMapping("/crear")
	public ResponseEntity<Mensaje> createEntity(@RequestParam("entidad") String entidad, @RequestParam("nombreUs") String nombreUs) throws JsonMappingException, JsonProcessingException{
		Educacion educacion = new ObjectMapper().readValue(entidad, Educacion.class);
		List<Usuario> usuarios = usuarioService.listaUsuario();
		for(Usuario usuario : usuarios) {
			if(usuario.getNombreUsuario().equals(nombreUs)) 
				educacion.setUsuario(usuario);
		}
		Educacion dbEducacion = educacionService.save(educacion);
		if(dbEducacion!=null) {
			return new ResponseEntity<Mensaje>(new Mensaje("Educacion creada con exito"), HttpStatus.OK);
		} else {
			return new ResponseEntity<Mensaje>(new Mensaje("Educacion no creada"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> updateEntity(@PathVariable("id") int id, @RequestParam("entidad") String entidad) throws JsonMappingException, JsonProcessingException,  IOException{
		
		Educacion educacion = new ObjectMapper().readValue(entidad, Educacion.class);
		
		if(!educacionService.existsById(id))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe");
		if(educacionService.existsByEntidad(educacion.getTitulo()) && educacionService.getByEntidad(educacion.getTitulo()).get().getId() != id)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("esa educacion ya existe");
		if(StringUtils.isBlank(educacion.getTitulo()))
			return new ResponseEntity<>(new Mensaje("La eduacion es obligatoria"), HttpStatus.BAD_REQUEST);
	
		Educacion educacionEntidad = educacionService.getOne(id).get();
		educacionEntidad.setEntidad(educacion.getEntidad());
		educacionEntidad.setTitulo(educacion.getTitulo());
		educacionEntidad.setFecha(educacion.getFecha());
		educacionEntidad.setUbicacion(educacion.getUbicacion());
		
		educacionService.save(educacionEntidad);
		return new ResponseEntity<>(new Mensaje("Educacion actualizada"), HttpStatus.OK);
		
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") int id){
		if(!educacionService.existsById(id)) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe");
		educacionService.delete(id);
		return new ResponseEntity<>(new Mensaje("Entidad eliminada"), HttpStatus.OK);
	}

}
