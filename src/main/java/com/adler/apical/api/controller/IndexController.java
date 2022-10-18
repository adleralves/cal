package com.adler.apical.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author adler
 */

@Controller
public class IndexController {
    
    @GetMapping("/cadastro")
    public ModelAndView getTeste() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}
