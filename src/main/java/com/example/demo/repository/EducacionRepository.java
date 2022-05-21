package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Educacion;

@Repository
public interface EducacionRepository extends JpaRepository<Educacion, Integer>{
	Optional<Educacion> findByEntidad (String entidad);
	boolean existsByEntidad (String entidad);
}
