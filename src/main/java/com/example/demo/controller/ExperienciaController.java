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
import com.example.demo.entity.Experiencia;
import com.example.demo.security.entity.Usuario;
import com.example.demo.security.service.UsuarioService;
import com.example.demo.service.ExperienciaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/auth/experiencias")
@CrossOrigin(origins = "*")
public class ExperienciaController {
	
	@Autowired
	ExperienciaService experienciaService;
	
	@Autowired
	UsuarioService usuarioService;

	@GetMapping("/")
	public ResponseEntity<List<Experiencia>> listExperiencia(){
		List<Experiencia> list = experienciaService.list();
		return new ResponseEntity<List<Experiencia>>(list, HttpStatus.OK); 
	}
	
	@PostMapping("/crear")
	public ResponseEntity<Mensaje> createEntity(@RequestParam("entidad") String entidad, @RequestParam("nombreUs") String nombreUs) throws JsonMappingException, JsonProcessingException{
		Experiencia experiencia = new ObjectMapper().readValue(entidad, Experiencia.class);
		List<Usuario> usuarios = usuarioService.listaUsuario();
		for(Usuario usuario : usuarios) {
			if(usuario.getNombreUsuario().equals(nombreUs)) 
				experiencia.setUsuario(usuario);
		}
		Experiencia dbExperiencia = experienciaService.save(experiencia);
		if(dbExperiencia!=null) {
			return new ResponseEntity<Mensaje>(new Mensaje("Experiencia creada con exito"), HttpStatus.OK);
		} else {
			return new ResponseEntity<Mensaje>(new Mensaje("Experiencia no creada"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> updateEntity(@PathVariable("id") int id, @RequestParam("entidad") String entidad) throws JsonMappingException, JsonProcessingException,  IOException{
		
		Experiencia experiencia = new ObjectMapper().readValue(entidad, Experiencia.class);
		
		if(!experienciaService.existsById(id))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe");
		if(experienciaService.existsByTitulo(experiencia.getTitulo()) && experienciaService.getByTitulo(experiencia.getTitulo()).get().getId() != id)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("esa experiencia ya existe");
		if(StringUtils.isBlank(experiencia.getTitulo()))
			return new ResponseEntity<>(new Mensaje("La experiencia es obligatoria"), HttpStatus.BAD_REQUEST);
	
		Experiencia experienciaEntidad = experienciaService.getOne(id).get();
		experienciaEntidad.setTitulo(experiencia.getTitulo());
		
		experienciaService.save(experienciaEntidad);
		return new ResponseEntity<>(new Mensaje("Experiencia actualizada"), HttpStatus.OK);
		
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") int id){
		if(!experienciaService.existsById(id)) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe");
		experienciaService.delete(id);
		return new ResponseEntity<>(new Mensaje("Entidad eliminada"), HttpStatus.OK);
	}
	
}
