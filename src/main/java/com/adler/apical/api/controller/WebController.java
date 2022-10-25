package com.adler.apical.api.controller;

import com.adler.apical.domain.model.Sala;
import com.adler.apical.domain.model.Usuario;
import com.adler.apical.domain.repository.AcessosRepository;
import com.adler.apical.domain.repository.SalaRepository;
import com.adler.apical.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author adler
 */
@Controller
public class WebController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AcessosRepository acessoRepository;
    
    @Autowired
    private SalaRepository salaRepository;

    @GetMapping("/admin")
    public ModelAndView listarUsuario() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.addObject("acessoList", acessoRepository.findAll());
        return mv;
    }

    @GetMapping("/newUsuario")
    public ModelAndView cadastroAluno(@ModelAttribute Usuario usuario) {
        ModelAndView mv = new ModelAndView("forms/usuarioForm");
        mv.addObject("usuario", usuario);
        return mv;
    }

    @PostMapping("criarUsuario")
    public ModelAndView criarUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:index");
        usuarioRepository.save(usuario);
        return mv;
    }
    
    @GetMapping("/newLab")
    public ModelAndView cadastroAluno(@ModelAttribute Sala sala) {
        ModelAndView mv = new ModelAndView("forms/labForm");
        mv.addObject("sala", sala);
        return mv;
    }

    @PostMapping("criarLab")
    public ModelAndView criarLab(Sala sala) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:index");
        salaRepository.save(sala);
        return mv;
    }
}
