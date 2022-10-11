package com.adler.apical.api.controller;

import com.adler.apical.domain.model.Acessos;
import com.adler.apical.domain.repository.AcessosRepository;
import com.adler.apical.domain.service.AcessoService;
import java.util.List;
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
 * Controller para criar/editar e excluir acesso dos laboratorios
 * @author adler
 */
@RestController
public class AcessosController {
    
    @Autowired
    private AcessosRepository acessosRepository;
    
    @Autowired
    private AcessoService acessoService;
    
    @GetMapping("/acessos")
    public List<Acessos> list() {
        return acessosRepository.findAll();
    }
    
    @PostMapping("/acessos/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public Acessos criar(@RequestBody Acessos acesso) {
        return acessosRepository.save(acesso);
    }
    
        // Endpoint para editar acesso
    @PutMapping("/acessos/{acessoID}")
    public ResponseEntity<Acessos> editar(@Valid @PathVariable Long acessoID,
                                          @RequestBody Acessos acesso) {
        
        //Verifica se o acesso existe
        if(!acessosRepository.existsById(acessoID)) {
            return ResponseEntity.notFound().build();
        }
        
        acesso.setId(acessoID);
        acesso = acessoService.salvar(acesso);
        return ResponseEntity.ok(acesso);
    }

    // Endpoint para excluir acesso
    @DeleteMapping("/acessos/{acessoID}")
    public ResponseEntity<Void> excluir(@PathVariable Long acessoID) {
        
        //Verifica se acesso existe ou não
        if (!acessosRepository.existsById(acessoID)) {
            return ResponseEntity.notFound().build();
        }

        acessoService.excluir(acessoID);
        return ResponseEntity.noContent().build();
    }
}
