package com.proyecto.ticketerra_web.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.ticketerra_web.servicio.UsuarioServicio;

@Controller
@RequestMapping("/usuarios")
public class RegistrarControlador {

    private final UsuarioServicio usuarioServicio;

    @Autowired
    public RegistrarControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }
    
    @GetMapping("/registrar")
    public ModelAndView registrarForm() {
    	ModelAndView mav = new ModelAndView("registrar");
    	mav.addObject("mensaje", "Por favor registrese para usar la pagina web");
    	return mav;
    }

    // Manejar el registro
    @PostMapping("/registrar")
    @ResponseBody
    public ResponseEntity<String> procesarRegistro(@RequestParam String nombreCompleto, 
                                                   @RequestParam String correo, 
                                                   @RequestParam String telefono, 
                                                   @RequestParam String codigoPostal, 
                                                   @RequestParam String contrasena) {
        // Llamar al servicio para registrar el usuario
        usuarioServicio.registrarUsuario(nombreCompleto, correo, telefono, codigoPostal, contrasena);

        return ResponseEntity.ok("Registro exitoso");
    }
}
