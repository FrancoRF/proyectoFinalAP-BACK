package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Experiencia;
import com.example.demo.repository.ExperienciaRepository;

@Service
@Transactional
public class ExperienciaService {
	
	@Autowired
	ExperienciaRepository experienciaRepository;
	
	public List<Experiencia> list(){
		return experienciaRepository.findAll();
	}
	
	public Optional<Experiencia> getOne(int id){
		return experienciaRepository.findById(id);
	}
	
	public Optional<Experiencia> getByTitulo (String titulo) {
		return experienciaRepository.findByTitulo(titulo);
	}
	
	public Experiencia save(Experiencia experiencia) {
		return experienciaRepository.save(experiencia);  
	}
	
	public void delete(int id) {
		experienciaRepository.deleteById(id);
	}
	
	public boolean existsById(int id) {
		return experienciaRepository.existsById(id);
	}
	
	public boolean existsByTitulo(String titulo) {
		return experienciaRepository.existsByTitulo(titulo);
	}

}
