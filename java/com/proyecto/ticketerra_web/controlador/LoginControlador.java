package com.proyecto.ticketerra_web.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.proyecto.ticketerra_web.servicio.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuarios")
public class LoginControlador {

    private UsuarioServicio usuarioServicio;

    @Autowired
    public LoginControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    // Mostrar la vista de login
    @GetMapping("/login")
    public ModelAndView loginForm() {
        ModelAndView mav = new ModelAndView("login"); // La vista login.jsp
        mav.addObject("mensaje", "Por favor, ingrese sus credenciales"); // Agrega datos al modelo
        return mav;
    }

    // Manejar el login con datos del formulario
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo, @RequestParam String contrasena, HttpSession session, Model model) {
        try {
            // Llamada al servicio para verificar las credenciales
            boolean loginExitoso = usuarioServicio.loginUsuario(correo, contrasena);

            if (loginExitoso) {
                session.setAttribute("usuario", correo);
                return "redirect:/usuarios/index"; // Redirigir al inicio si es exitoso
            } else {
                model.addAttribute("mensaje", "Correo o contraseña incorrectos");
                return "login"; // Retorna a la vista de login con un mensaje de error
            }
        } catch (Exception e) {
            model.addAttribute("mensaje", "Ocurrió un error al procesar su solicitud. Intente nuevamente más tarde.");
            return "login"; // Retorna con un mensaje de error genérico
        }
    }

    // Vista para recuperar la contraseña
    @GetMapping("/recuperar")
    public String mostrarFormularioRecuperacion() {
        return "recuperar"; // Vista para recuperación de contraseña
    }

    // Enviar correo para la recuperación
    @PostMapping("/recuperar")
    public String enviarCorreoRecuperacion(@RequestParam("correo") String correo, Model model) {
        try {
            // Llamar al servicio para enviar el token de recuperación
            boolean enviado = usuarioServicio.enviarTokenRecuperacion(correo);
            
            if (enviado) {
                model.addAttribute("mensaje", "Correo enviado. Por favor revisa tu bandeja.");
                return "mensaje_confirmacion"; // Vista de confirmación de envío
            } else {
                model.addAttribute("error", "No se pudo enviar el correo. Inténtalo más tarde.");
                return "recuperar"; // Retorna a la vista de recuperación con error
            }
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error al enviar el correo. Intenta nuevamente.");
            return "recuperar"; // Retorna a la vista de recuperación con error
        }
    }
}
