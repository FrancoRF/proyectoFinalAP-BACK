package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Acercade;

@Repository
public interface AcercadeRepository extends JpaRepository<Acercade, Integer>{
	
	Optional<Acercade> findByTexto (String texto);
	boolean existsByTexto (String texto);
	
}
