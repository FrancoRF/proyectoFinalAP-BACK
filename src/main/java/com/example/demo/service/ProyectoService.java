package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Proyecto;
import com.example.demo.repository.ProyectoRepository;

@Service
@Transactional
public class ProyectoService {
	
	@Autowired
	ProyectoRepository proyectoRepository;
	
	public List<Proyecto> list(){
		return proyectoRepository.findAll();
	}
	
	public Optional<Proyecto> getOne(int id){
		return proyectoRepository.findById(id);
	}
	
	public Optional<Proyecto> getByTitulo (String titulo) {
		return proyectoRepository.findByTitulo(titulo);
	}
	
	public Proyecto save(Proyecto proyecto) {
		return proyectoRepository.save(proyecto);  
	}
	
	public void delete(int id) {
		proyectoRepository.deleteById(id);
	}
	
	public boolean existsById(int id) {
		return proyectoRepository.existsById(id);
	}
	
	public boolean existsByTitulo(String titulo) {
		return proyectoRepository.existsByTitulo(titulo);
	}

}
