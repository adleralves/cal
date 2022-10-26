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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    
    
    // Trabalhando com acessos (listando)

    @GetMapping("/admin")
    public ModelAndView listarAcessos() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.addObject("acessoList", acessoRepository.findAll());
        return mv;
    }
    
    // Trabalhando com usuarios (listando/criando)

    @RequestMapping(value = "/newUsuario", method = { RequestMethod.GET, RequestMethod.POST})
    public ModelAndView teste(@ModelAttribute Usuario usuario) {
        ModelAndView mv = new ModelAndView("forms/usuarioForm");
        mv.addObject("usuario", usuario);
        mv.addObject("usuarioList", usuarioRepository.findAll());
        return mv;
    }

    @PostMapping("criarUsuario")
    public ModelAndView criarUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:admin");
        usuarioRepository.save(usuario);
        return mv;
    }
    
    // Trabalhando com salas (listando/criando)
    
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
