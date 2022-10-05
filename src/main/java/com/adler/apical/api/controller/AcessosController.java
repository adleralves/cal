package com.adler.apical.api.controller;

import com.adler.apical.domain.model.Acessos;
import com.adler.apical.domain.repository.AcessosRepository;
import java.util.List;
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
public class AcessosController {
    
    @Autowired
    private AcessosRepository acessosRepository;
    
    @GetMapping("/acessos")
    public List<Acessos> list() {
        return acessosRepository.findAll();
    }
    
    @PostMapping("/acessos/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public Acessos criar(@RequestBody Acessos acesso) {
        return acessosRepository.save(acesso);
    }
}
