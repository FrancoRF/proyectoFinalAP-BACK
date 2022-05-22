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
import com.example.demo.entity.Acercade;
import com.example.demo.security.entity.Usuario;
import com.example.demo.security.service.UsuarioService;
import com.example.demo.service.AcercadeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/auth/contenedores")
@CrossOrigin(origins = "*")
public class AcercadeController {
	
	@Autowired
	AcercadeService acercadeService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/")
	public ResponseEntity<List<Acercade>> listAcercade(){
		List<Acercade> list = acercadeService.list();
		return new ResponseEntity<List<Acercade>>(list, HttpStatus.OK); 
	}
	
	@PostMapping("/crear")
	public ResponseEntity<Mensaje> createEntity(@RequestParam("entidad") String entidad, @RequestParam("nombreUs") String nombreUs) throws JsonMappingException, JsonProcessingException{
		Acercade acercade = new ObjectMapper().readValue(entidad, Acercade.class);
		List<Usuario> usuarios = usuarioService.listaUsuario();
		for(Usuario usuario : usuarios) {
			if(usuario.getNombreUsuario().equals(nombreUs)) 
				acercade.setUsuario(usuario);
		}
		Acercade dbAcercade = acercadeService.save(acercade);
		if(dbAcercade!=null) {
			return new ResponseEntity<Mensaje>(new Mensaje("Objeto acercade creado con exito"), HttpStatus.OK);
		} else {
			return new ResponseEntity<Mensaje>(new Mensaje("Objeto acercade no creado"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> updateEntity(@PathVariable("id") int id, @RequestParam("entidad") String entidad) throws JsonMappingException, JsonProcessingException,  IOException{
		
		Acercade acercade = new ObjectMapper().readValue(entidad, Acercade.class);
		
		if(!acercadeService.existsById(id))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe");
		if(acercadeService.existsByTexto(acercade.getTexto()) && acercadeService.getByTexto(acercade.getTexto()).get().getId() != id)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ese texto ya existe");
		if(StringUtils.isBlank(acercade.getTexto()))
			return new ResponseEntity<>(new Mensaje("El texto es obligatorio"), HttpStatus.BAD_REQUEST);
	
		Acercade acercadeEntidad = acercadeService.getOne(id).get();
		acercadeEntidad.setTexto(acercade.getTexto());
		
		acercadeService.save(acercadeEntidad);
		return new ResponseEntity<>(new Mensaje("Texto descriptivo actualizado"), HttpStatus.OK);
		
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") int id){
		if(!acercadeService.existsById(id)) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe");
		acercadeService.delete(id);
		return new ResponseEntity<>(new Mensaje("Entidad eliminada"), HttpStatus.OK);
	}
	
	

}
