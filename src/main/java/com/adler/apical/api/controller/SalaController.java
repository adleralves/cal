package com.adler.apical.api.controller;

import com.adler.apical.domain.model.Sala;
import com.adler.apical.domain.repository.SalaRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    
    @PostMapping("/sala/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public Sala adicionar(@Valid @RequestBody Sala sala) {
        return salaRepository.save(sala);
    }
    
    @GetMapping("/salas")
    public List<Sala> listar() {
        return salaRepository.findAll();
    }
    
    // Endpoint para excluir sala
    @DeleteMapping("/sala/{salaID}")
    public ResponseEntity<Void> excluir(@PathVariable Long salaID) {
        
        //Verifica se usuario existe ou não
        if (!salaRepository.existsById(salaID)) {
            return ResponseEntity.notFound().build();
        }

        salaRepository.deleteById(salaID);
        return ResponseEntity.noContent().build();
    }
    
    /*
    Enviando e trabalhando dados com o Thymeleaf/HTML
    */
    
    @RequestMapping(value = "/newLab", method = { RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listarLab(@ModelAttribute Sala sala) {
        ModelAndView mv = new ModelAndView("forms/labForm");
        mv.addObject("sala", sala);
        mv.addObject("salaList", salaRepository.findAll());
        return mv;
    }

    @PostMapping("criarLab")
    public ModelAndView criarLab(Sala sala) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:newLab");
        salaRepository.save(sala);
        return mv;
    }
}

