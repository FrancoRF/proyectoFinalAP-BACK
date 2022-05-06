package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Perfil;
import com.example.demo.repository.PerfilRepository;

@Service
@Transactional
public class PerfilService {
	
	@Autowired
	PerfilRepository perfilRepository;
	
	public List<Perfil> list(){
		return perfilRepository.findAll();
	}
	
	public Optional<Perfil> getOne(int id){
		return perfilRepository.findById(id);
	}
	
	public Optional<Perfil> getByNombre (String nombre) {
		return perfilRepository.findByNombre(nombre);
	}
	
	public Perfil save(Perfil perfil) {
		return perfilRepository.save(perfil);  
	}
	
	public void delete(int id) {
		perfilRepository.deleteById(id);
	}
	
	public boolean existsById(int id) {
		return perfilRepository.existsById(id);
	}
	
	public boolean existsByNombre(String nombre) {
		return perfilRepository.existsByNombre(nombre);
	}

}
