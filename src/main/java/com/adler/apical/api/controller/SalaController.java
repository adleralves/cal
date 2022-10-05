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
 * Este controller é feito apenas para o hardware receber o estado da sala, se
 * esta aberta ou fechada
 * @author adler
 */
@RestController
public class SalaController {
    
    @Autowired
    private SalaRepository salaRepository;
    
    //Busca a sala pelo ID
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
