package com.example.demo.seguridad.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.seguridad.entity.Rol;
import com.example.demo.seguridad.enums.RolNombre;

@Repository
public interface RolRepo extends JpaRepository<Rol, Integer>{
	
	Optional<Rol> findByRolNombre(RolNombre rolNombre);

}
