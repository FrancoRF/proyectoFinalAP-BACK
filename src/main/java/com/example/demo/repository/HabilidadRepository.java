package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Habilidad;

@Repository
public interface HabilidadRepository extends JpaRepository<Habilidad, Integer> {
	
	Optional<Habilidad> findByTitulo (String titulo);
	boolean existsByTitulo (String titulo);

}
