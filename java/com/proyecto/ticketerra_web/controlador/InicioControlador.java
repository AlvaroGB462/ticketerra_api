package com.proyecto.ticketerra_web.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usuarios")
public class InicioControlador {

    // Mostrar la vista de inicio
    @GetMapping("/index")
    public ModelAndView loginForm() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
    
 // Mostrar la vista de inicio (en la raíz del sitio)
    @GetMapping("/")
    public ModelAndView inicio() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
}

