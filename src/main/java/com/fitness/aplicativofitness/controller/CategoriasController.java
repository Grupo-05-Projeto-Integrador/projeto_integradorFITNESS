package com.fitness.aplicativofitness.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fitness.aplicativofitness.model.Categorias;
import com.fitness.aplicativofitness.repository.CategoriaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriasController {
	@Autowired
    private CategoriaRepository categoriaRepository;
    
    @GetMapping
    public ResponseEntity<List<Categorias>> getAll(){
        return ResponseEntity.ok(categoriaRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Categorias> getById(@PathVariable Long id){
        return categoriaRepository.findById(id)
            .map(resposta -> ResponseEntity.ok(resposta))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Categorias>> getByTitle(@PathVariable 
    String descricao){
        return ResponseEntity.ok(categoriaRepository
            .findAllByDescricaoContainingIgnoreCase(descricao));
    }
    
    @PostMapping
    public ResponseEntity<Categorias> post(@Valid @RequestBody Categorias categorias){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaRepository.save(categorias));
    }
    
    @PutMapping
    public ResponseEntity<Categorias> put(@Valid @RequestBody Categorias categorias){
        return categoriaRepository.findById(categorias.getId())
            .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
            .body(categoriaRepository.save(categorias)))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Categorias> categorias = categoriaRepository.findById(id);
        
        if(categorias.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        
        categoriaRepository.deleteById(id);              
    }

	

}
