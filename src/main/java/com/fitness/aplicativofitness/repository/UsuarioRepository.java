package com.fitness.aplicativofitness.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.fitness.aplicativofitness.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Optional<Usuario> findById(Long id);

	
	public List <Usuario> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);
}
