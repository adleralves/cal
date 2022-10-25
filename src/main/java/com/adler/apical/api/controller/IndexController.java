package com.adler.apical.api.controller;

import com.adler.apical.domain.model.Usuario;
import com.adler.apical.domain.repository.AcessosRepository;
import com.adler.apical.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author adler
 */
@Controller
public class IndexController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AcessosRepository acessoRepository;

    /*
    @GetMapping("/cadastro")
    public ModelAndView getTeste() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
     */

 /*
    @GetMapping("/usuarios/listar")
    public ModelAndView listarUsuario() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.addObject("usuarioList", usuarioRepository.findAll());
        mv.addObject("acessoList", acessoRepository.findAll());
        return mv;
    }
     */
    @GetMapping("/admin")
    public ModelAndView listarUsuario() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.addObject("acessoList", acessoRepository.findAll());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroAluno(@ModelAttribute Usuario usuario) {
        ModelAndView mv = new ModelAndView("forms/usuarioForm");
        mv.addObject("usuario", usuario);
        return mv;
    }
}
