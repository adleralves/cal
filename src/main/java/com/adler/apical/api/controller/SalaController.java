/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adler.apical.api.controller;

import com.adler.apical.domain.model.Sala;
import com.adler.apical.domain.repository.SalaRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author adler
 */
@RestController
public class SalaController {
    
    @Autowired
    private SalaRepository salaRepository;
    
    @GetMapping("/sala/{salaID}")
    public ResponseEntity<Sala> listar(@PathVariable Long salaID) {
        Optional<Sala> sala = salaRepository.findById(salaID);

        //Verifica se usuario existe ou não
        if (sala.isPresent()) {
            return ResponseEntity.ok(sala.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
