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
import com.example.demo.entity.Habilidad;
import com.example.demo.security.entity.Usuario;
import com.example.demo.security.service.UsuarioService;
import com.example.demo.service.HabilidadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/auth/habilidades")
@CrossOrigin(origins = "*")
public class HabilidadController {
	
	@Autowired
	HabilidadService habilidadService;
	
	@Autowired
	UsuarioService usuarioService;

	@GetMapping("/")
	public ResponseEntity<List<Habilidad>> listExperiencia(){
		List<Habilidad> list = habilidadService.list();
		return new ResponseEntity<List<Habilidad>>(list, HttpStatus.OK); 
	}
	
	@PostMapping("/crear")
	public ResponseEntity<Mensaje> createEntity(@RequestParam("entidad") String entidad, @RequestParam("nombreUs") String nombreUs) throws JsonMappingException, JsonProcessingException{
		Habilidad habilidad = new ObjectMapper().readValue(entidad, Habilidad.class);
		List<Usuario> usuarios = usuarioService.listaUsuario();
		for(Usuario usuario : usuarios) {
			if(usuario.getNombreUsuario().equals(nombreUs)) 
				habilidad.setUsuario(usuario);
		}
		Habilidad dbHabilidad = habilidadService.save(habilidad);
		if(dbHabilidad!=null) {
			return new ResponseEntity<Mensaje>(new Mensaje("Habilidad creada con exito"), HttpStatus.OK);
		} else {
			return new ResponseEntity<Mensaje>(new Mensaje("Habilidad no creada"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> updateEntity(@PathVariable("id") int id, @RequestParam("entidad") String entidad) throws JsonMappingException, JsonProcessingException,  IOException{
		
		Habilidad habilidad = new ObjectMapper().readValue(entidad, Habilidad.class);
		
		if(!habilidadService.existsById(id))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe");
		if(habilidadService.existsByTitulo(habilidad.getTitulo()) && habilidadService.getByTitulo(habilidad.getTitulo()).get().getId() != id)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("esa habilidad ya existe");
		if(StringUtils.isBlank(habilidad.getTitulo()))
			return new ResponseEntity<>(new Mensaje("La habilidad es obligatoria"), HttpStatus.BAD_REQUEST);
	
		Habilidad habilidadEntidad = habilidadService.getOne(id).get();
		habilidadEntidad.setTitulo(habilidad.getTitulo());
		habilidadEntidad.setPorcentaje(habilidad.getPorcentaje());
		
		habilidadService.save(habilidadEntidad);
		return new ResponseEntity<>(new Mensaje("habilidad actualizada"), HttpStatus.OK);
		
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") int id){
		if(!habilidadService.existsById(id)) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe");
		habilidadService.delete(id);
		return new ResponseEntity<>(new Mensaje("Habilidad eliminada"), HttpStatus.OK);
	}

}
