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
    
    //Método salva usuario(pode ser para novo usuario, ou editar)
    
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    //Método deleta o usuario com o id selecionado
    
    public void excluir(Long usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }
}
