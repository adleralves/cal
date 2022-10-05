package com.adler.apical.api.controller;

import com.adler.apical.domain.model.Usuario;
import com.adler.apical.domain.repository.UsuarioRepository;
import com.adler.apical.domain.service.UsuarioService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author adler
 */
@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario")
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
    
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario adicionar(@Valid @RequestBody Usuario usuario) {
        
        return usuarioService.salvar(usuario);
    }
}
