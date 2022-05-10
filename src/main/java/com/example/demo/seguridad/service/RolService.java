package com.example.demo.seguridad.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.seguridad.entity.Rol;
import com.example.demo.seguridad.enums.RolNombre;
import com.example.demo.seguridad.repository.RolRepo;

@Service
@Transactional
public class RolService {
	
	@Autowired
	RolRepo rolRepository;
	
	public Optional<Rol> getByRolNombre(RolNombre rolNombre){
		return rolRepository.findByRolNombre(rolNombre);
	}
	
	public void guardar(Rol rol) {
		rolRepository.save(rol);
	}

}
