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
import com.example.demo.entity.Proyecto;
import com.example.demo.security.entity.Usuario;
import com.example.demo.security.service.UsuarioService;
import com.example.demo.service.ProyectoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/auth/proyectos")
@CrossOrigin(origins = "*")
public class ProyectoController {
	
	@Autowired
	ProyectoService proyectoService;
	
	@Autowired
	UsuarioService usuarioService;

	@GetMapping("/")
	public ResponseEntity<List<Proyecto>> listProyecto(){
		List<Proyecto> list = proyectoService.list();
		return new ResponseEntity<List<Proyecto>>(list, HttpStatus.OK); 
	}
	
	@PostMapping("/crear")
	public ResponseEntity<Mensaje> createEntity(@RequestParam("entidad") String entidad, @RequestParam("nombreUs") String nombreUs) throws JsonMappingException, JsonProcessingException{
		Proyecto proyecto = new ObjectMapper().readValue(entidad, Proyecto.class);
		List<Usuario> usuarios = usuarioService.listaUsuario();
		for(Usuario usuario : usuarios) {
			if(usuario.getNombreUsuario().equals(nombreUs)) 
				proyecto.setUsuario(usuario);
		}
		Proyecto dbProyecto = proyectoService.save(proyecto);
		if(dbProyecto!=null) {
			return new ResponseEntity<Mensaje>(new Mensaje("Proyecto creado con exito"), HttpStatus.OK);
		} else {
			return new ResponseEntity<Mensaje>(new Mensaje("Proyecto no creado"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> updateEntity(@PathVariable("id") int id, @RequestParam("entidad") String entidad) throws JsonMappingException, JsonProcessingException,  IOException{
		
		Proyecto proyecto = new ObjectMapper().readValue(entidad, Proyecto.class);
		
		if(!proyectoService.existsById(id))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe");
		if(proyectoService.existsByTitulo(proyecto.getTitulo()) && proyectoService.getByTitulo(proyecto.getTitulo()).get().getId() != id)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ese proyecto ya existe");
		if(StringUtils.isBlank(proyecto.getTitulo()))
			return new ResponseEntity<>(new Mensaje("El proyecto es obligatorio"), HttpStatus.BAD_REQUEST);
	
		Proyecto proyectoEntidad = proyectoService.getOne(id).get();
		proyectoEntidad.setTitulo(proyecto.getTitulo());
		proyectoEntidad.setDescripcion(proyecto.getDescripcion());
		proyectoEntidad.setLink(proyecto.getLink());
		
		proyectoService.save(proyectoEntidad);
		return new ResponseEntity<>(new Mensaje("Proyecto actualizado"), HttpStatus.OK);
		
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") int id){
		if(!proyectoService.existsById(id)) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe");
		proyectoService.delete(id);
		return new ResponseEntity<>(new Mensaje("Proyecto eliminado"), HttpStatus.OK);
	}

}
