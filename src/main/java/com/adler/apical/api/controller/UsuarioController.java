package com.adler.apical.api.controller;

import com.adler.apical.domain.model.Usuario;
import com.adler.apical.domain.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author adler
 */
@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuario")
    public List<Usuario> list() {
        return usuarioRepository.findAll();
    }
}
