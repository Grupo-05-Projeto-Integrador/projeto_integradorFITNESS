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

import com.fitness.aplicativofitness.model.Usuario;
import com.fitness.aplicativofitness.repository.UsuarioRepository;
import com.fitness.aplicativofitness.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
    private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	

	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> getAll() {
	    // Busca todos os usuários
	    List<Usuario> usuarios = usuarioRepository.findAll();
	    
	    // Verifica se existem usuários
	    if (usuarios.isEmpty()) {
	        return ResponseEntity.noContent().build();  // Retorna 204 se não houver usuários
	    }

	    // altera sobre os usuários e calcula o IMC para cada um
	    usuarios.forEach(usuario -> {
	        usuarioService.calcularImc(usuario.getId());  // Atualiza o IMC de cada usuário
	    });

	    // Retorna a lista de usuários com IMC calculado
	    return ResponseEntity.ok(usuarios);
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable Long id) {
	    // Buscar o usuário pelo ID
	    Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

	    // Verifica se o usuário existe
	    if (usuarioOptional.isPresent()) {
	        // Chama o método calcularImc para atualizar o IMC do usuário
	        Usuario usuario = usuarioOptional.get();
	        
	        // Atualiza o IMC, se possível
	        usuarioService.calcularImc(id);
	        
	        // Retorna o usuário com o IMC calculado
	        return ResponseEntity.ok(usuario);
	    } else {
	        // Retorna 404 caso o usuário não seja encontrado
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/nome/{nome}") // Consultar por Nome
	public ResponseEntity<List<Usuario>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(usuarioRepository.findAllByNomeContainingIgnoreCase(nome));
	}	
	
	@GetMapping("/calcular-imc/{id}")
    public ResponseEntity<Usuario> calcularImc(@PathVariable Long id) {
        return usuarioService.calcularImc(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
	
	@PostMapping // Criar um cadastro
	public ResponseEntity<Usuario> post(@Valid @RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
	}

	@PutMapping // Criar método Atualizar
	public ResponseEntity<Usuario> put(@Valid @RequestBody Usuario usuario) {
		return usuarioRepository.findById(usuario.getId()).map(
				resposta -> ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuario)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)   // Criar o método Deletar
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Usuario> CadastroFuncionario = usuarioRepository.findById(id);

		if (CadastroFuncionario.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		usuarioRepository.deleteById(id);
	}
	
}
