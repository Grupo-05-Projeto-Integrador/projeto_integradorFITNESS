package com.fitness.aplicativofitness.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.aplicativofitness.model.Usuario;
import com.fitness.aplicativofitness.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Calcula o IMC de um usuário com base no ID.
     * 
     * @param id ID do usuário.
     * @return Optional contendo o usuário com o IMC calculado ou vazio se o usuário não existir.
     */
    public Optional<Usuario> calcularImc(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            // Valida os dados de altura e peso
            if (usuario.getAltura() == null || usuario.getPeso() == null || 
                usuario.getAltura() <= 0 || usuario.getPeso() <= 0) {
                throw new IllegalArgumentException("Altura e Peso devem ser valores positivos e não nulos.");
            }

            // Calcula o IMC
            double imc = usuario.getPeso() / (usuario.getAltura() * usuario.getAltura());
            usuario.setImc(imc);

            // Atualiza o IMC no banco de dados
            usuarioRepository.save(usuario);

            return usuario;
        });
    }
}
