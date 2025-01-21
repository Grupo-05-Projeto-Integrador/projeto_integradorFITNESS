package com.aplicativofit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aplicativofit.model.Categorias;

public interface CategoriaRepository extends JpaRepository<Categorias, Long> {
		
		public List<Categorias> findAllByDescricaoContainingIgnoreCase(String descricao);


	}

