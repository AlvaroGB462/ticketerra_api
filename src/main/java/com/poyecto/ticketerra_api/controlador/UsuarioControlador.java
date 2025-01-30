package com.poyecto.ticketerra_api.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poyecto.ticketerra_api.modelo.Usuario;
import com.poyecto.ticketerra_api.servicio.UsuarioServicio;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestParam String nombreCompleto, @RequestParam String correo,
                                                   @RequestParam String telefono, @RequestParam String codigoPostal,
                                                   @RequestParam String contrasena) {
        try {
            usuarioService.registrarUsuario(nombreCompleto, correo, telefono, codigoPostal, contrasena);
            return new ResponseEntity<>("Usuario registrado correctamente", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginUsuario(@RequestParam String correo, @RequestParam String contrasena) {
        Usuario usuario = usuarioService.buscarUsuarioPorCorreo(correo);
       

        if (usuario != null && usuarioService.compararContrasena(contrasena, usuario.getContrasena())) {
            return new ResponseEntity<>("Login exitoso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Correo o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PostMapping("/recuperar")
    public ResponseEntity<String> recuperarContrasena(@RequestParam String correo) {
        boolean correoEnviado = usuarioService.enviarTokenRecuperacion(correo);

        if (correoEnviado) {
            return new ResponseEntity<>("Se ha enviado un enlace de recuperación a tu correo", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("El correo no está registrado o ocurrió un error", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/restablecer")
    public ResponseEntity<String> restablecerContrasena(@RequestParam String token, @RequestParam String nuevaContrasena) {
        Usuario usuario = usuarioService.buscarUsuarioPorToken(token);

        if (usuario == null || usuario.getTokenExpiracion() < System.currentTimeMillis()) {
            return new ResponseEntity<>("Token inválido o expirado", HttpStatus.BAD_REQUEST);
        }

        usuarioService.restablecerContrasena(usuario, nuevaContrasena);
        return new ResponseEntity<>("Contraseña restablecida correctamente", HttpStatus.OK);
    }

    @GetMapping("/confirmar")
    public ResponseEntity<String> confirmarRegistro(@RequestParam String token) {
        boolean confirmado = usuarioService.confirmarRegistro(token);

        if (confirmado) {
            return new ResponseEntity<>("Registro confirmado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Token inválido o expirado", HttpStatus.BAD_REQUEST);
        }
    }
}
