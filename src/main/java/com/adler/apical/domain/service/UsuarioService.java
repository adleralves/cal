package com.adler.apical.domain.service;

import com.adler.apical.domain.model.Usuario;
import com.adler.apical.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author adler
 */

@Service
public class UsuarioService {
        
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public void excluir(Long clienteId) {
        usuarioRepository.deleteById(clienteId);
    }
}
