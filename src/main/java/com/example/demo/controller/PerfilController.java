package com.example.demo.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.Mensaje;
import com.example.demo.entity.Perfil;
import com.example.demo.security.entity.Usuario;
import com.example.demo.security.service.UsuarioService;
import com.example.demo.service.PerfilService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/auth/perfiles")
@CrossOrigin(origins = "*")
public class PerfilController {
	
	@Autowired
	PerfilService perfilService;
	
	@Autowired
	UsuarioService usuarioService;

	@GetMapping("/")
	public ResponseEntity<List<Perfil>> listExperiencia(){
		List<Perfil> list = perfilService.list();
		String base64uno = null;
		String base64dos = null;
		for(Perfil imagen : list) {
			base64uno = Base64.getEncoder().encodeToString(imagen.getImagenPer());
			base64dos = Base64.getEncoder().encodeToString(imagen.getImagenPor());
			String nombreImagenUno = imagen.getImgPerfil();
			String nombreImagenDos = imagen.getImgPortada();
			String extencionUno = FilenameUtils.getExtension(nombreImagenUno);
			String extencionDos = FilenameUtils.getExtension(nombreImagenDos);
			String addUno = ("data:image/"+extencionUno+";base64,"+base64uno);
			String addDos = ("data:image/"+extencionDos+";base64,"+base64dos);
			imagen.setImgPerfil(addUno);
			imagen.setImgPortada(addDos);
		}
		return new ResponseEntity<List<Perfil>>(list, HttpStatus.OK); 
	}
	
	@PostMapping("/crear")
	public ResponseEntity<Mensaje> createEntity(@RequestParam("imagenPortada") MultipartFile imagenPortada ,@RequestParam("imagenPerfil") MultipartFile imagenPerfil ,@RequestParam("entidad") String entidad, @RequestParam("nombreUs") String nombreUs) throws IOException{
		Perfil perfil = new ObjectMapper().readValue(entidad, Perfil.class);
		perfil.setImagenPer(imagenPerfil.getBytes());
		perfil.setImgPerfil(imagenPerfil.getOriginalFilename());
		perfil.setImagenPor(imagenPortada.getBytes());
		perfil.setImgPortada(imagenPortada.getOriginalFilename());
		List<Usuario> usuarios = usuarioService.listaUsuario();
		for(Usuario usuario : usuarios) {
			if(usuario.getNombreUsuario().equals(nombreUs)) 
				perfil.setUsuario(usuario);
		}
		Perfil dbPerfil = perfilService.save(perfil);
		
		if(dbPerfil!=null) {
			return new ResponseEntity<Mensaje>(new Mensaje("Experiencia creada con exito"), HttpStatus.OK);
		} else {
			return new ResponseEntity<Mensaje>(new Mensaje("Experiencia no creada"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> updateEntity(@PathVariable("id") int id,@RequestParam("imagenPortadaEditada") MultipartFile imagenPortada ,@RequestParam("imagenPerfilEditada") MultipartFile imagenPerfil ,@RequestParam("entidad") String entidad) throws JsonMappingException, JsonProcessingException,  IOException{
		
		Perfil perfilEntidad = new ObjectMapper().readValue(entidad, Perfil.class);
		
		if(!perfilService.existsById(id))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe");
		if(StringUtils.isBlank(perfilEntidad.getNombre()))
			return new ResponseEntity<>(new Mensaje("El perfil es obligatorio"), HttpStatus.BAD_REQUEST);
		
		perfilEntidad.setImagenPer(imagenPerfil.getBytes());
		perfilEntidad.setImgPerfil(imagenPerfil.getOriginalFilename());
		perfilEntidad.setImagenPor(imagenPortada.getBytes());
		perfilEntidad.setImgPortada(imagenPortada.getOriginalFilename());
	
		Perfil perfil = perfilService.getOne(id).get();
		perfil.setNombre(perfilEntidad.getNombre());
		perfil.setTitulo(perfilEntidad.getTitulo());
		perfil.setUbicacion(perfilEntidad.getUbicacion());
		perfil.setEmail(perfilEntidad.getEmail());
		perfil.setImagenPer(perfilEntidad.getImagenPer());
		perfil.setImgPerfil(perfilEntidad.getImgPerfil());
		perfil.setImagenPor(perfilEntidad.getImagenPor());
		perfil.setImgPortada(perfilEntidad.getImgPortada());
		
		perfilService.save(perfil);
		return new ResponseEntity<>(new Mensaje("Perfil actualizado"), HttpStatus.OK);
		
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") int id){
		if(!perfilService.existsById(id)) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe");
		perfilService.delete(id);
		return new ResponseEntity<>(new Mensaje("Perfil eliminado"), HttpStatus.OK);
	}

}
