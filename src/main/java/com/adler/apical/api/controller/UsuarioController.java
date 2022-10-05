package com.adler.apical.api.controller;

import com.adler.apical.domain.model.Usuario;
import com.adler.apical.domain.repository.UsuarioRepository;
import com.adler.apical.domain.service.UsuarioService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    // Endpoint listando todos usuarios
    @GetMapping("/usuarios")
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    // Endpoint que lista por id
    @GetMapping("usuarios/{usuarioID}")
    public ResponseEntity<Usuario> buscar(@PathVariable Long usuarioID) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioID);

        //Verifica se usuario existe ou não
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para adicionar novo usuario
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario adicionar(@Valid @RequestBody Usuario usuario) {
        return usuarioService.salvar(usuario);
    }
    
    // Endpoint para editar usuario
    @PutMapping("/usuarios/{usuarioID}")
    public ResponseEntity<Usuario> editar(@Valid @PathVariable Long usuarioID,
                                          @RequestBody Usuario usuario) {
        
        //Verifica se o usuario existe
        if(!usuarioRepository.existsById(usuarioID)) {
            return ResponseEntity.notFound().build();
        }
        
        usuario.setId(usuarioID);
        usuario = usuarioService.salvar(usuario);
        return ResponseEntity.ok(usuario);
    }

    // Endpoint para excluir usuario
    @DeleteMapping("/usuarios/{usuarioID}")
    public ResponseEntity<Void> excluir(@PathVariable Long usuarioID) {
        
        //Verifica se usuario existe ou não
        if (!usuarioRepository.existsById(usuarioID)) {
            return ResponseEntity.notFound().build();
        }

        usuarioService.excluir(usuarioID);
        return ResponseEntity.noContent().build();
    }
}
