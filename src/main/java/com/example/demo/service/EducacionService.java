package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Educacion;
import com.example.demo.repository.EducacionRepository;

@Service
@Transactional
public class EducacionService {
	
	@Autowired
	EducacionRepository educacionRepository;
	
	public List<Educacion> list(){
		return educacionRepository.findAll();
	}
	
	public Optional<Educacion> getOne(int id){
		return educacionRepository.findById(id);
	}
	
	public Optional<Educacion> getByEntidad (String entidad) {
		return educacionRepository.findByEntidad(entidad);
	}
	
	public Educacion save(Educacion educacion) {
		return educacionRepository.save(educacion);  
	}
	
	public void delete(int id) {
		educacionRepository.deleteById(id);
	}
	
	public boolean existsById(int id) {
		return educacionRepository.existsById(id);
	}
	
	public boolean existsByEntidad(String entidad) {
		return educacionRepository.existsByEntidad(entidad);
	}

}
