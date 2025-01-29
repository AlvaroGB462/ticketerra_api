package com.poyecto.ticketerra_api.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poyecto.ticketerra_api.modelo.RecuperacionRequest;
import com.poyecto.ticketerra_api.modelo.Usuario;
import com.poyecto.ticketerra_api.servicio.UsuarioServicio;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioService.registrarUsuario(usuario.getNombreCompleto(), usuario.getCorreo(), usuario.getTelefono(),
                    usuario.getCodigoPostal(), usuario.getContrasena());
            return new ResponseEntity<>("Usuario registrado correctamente", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUsuario(@RequestBody Usuario usuario) {
        boolean loginExitoso = usuarioService.loginUsuario(usuario.getCorreo(), usuario.getContrasena());

        if (loginExitoso) {
            return new ResponseEntity<>("Login exitoso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Correo o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
        }
    }

    // Nuevo endpoint para buscar un usuario por correo
    @GetMapping("/buscarPorCorreo")
    public ResponseEntity<Usuario> buscarPorCorreo(@RequestParam String correo) {
        Usuario usuario = usuarioService.buscarUsuarioPorCorreo(correo);

        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para manejar la solicitud de recuperación de contraseña
    @PostMapping("/recuperar")
    public ResponseEntity<String> recuperarContrasena(@RequestBody RecuperacionRequest request) {
        boolean correoEnviado = usuarioService.enviarTokenRecuperacion(request.getCorreo());

        if (correoEnviado) {
            return new ResponseEntity<>("Se ha enviado un enlace de recuperación a tu correo", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("El correo no está registrado o ocurrió un error", HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint para restablecer la contraseña
    @PostMapping("/restablecer")
    public ResponseEntity<String> restablecerContrasena(@RequestParam String token,
            @RequestParam String nuevaContrasena) {

        Usuario usuario = usuarioService.buscarUsuarioPorToken(token);

        if (usuario == null || usuario.getTokenExpiracion() < System.currentTimeMillis()) {
            return new ResponseEntity<>("Token inválido o expirado", HttpStatus.BAD_REQUEST);
        }

        // Actualizamos la contraseña
        String contrasenaHasheada = usuarioService.hashPassword(nuevaContrasena);
        usuario.setContrasena(contrasenaHasheada);
        usuario.setTokenRecuperacion(null); // Limpiar el token después de su uso
        usuario.setTokenExpiracion(0);
        usuarioService.actualizarUsuario(usuario);

        return new ResponseEntity<>("Contraseña restablecida correctamente", HttpStatus.OK);
    }
    
 // Endpoint para confirmar el registro
    @GetMapping("/confirmar")
    public ResponseEntity<String> confirmarRegistro(@RequestParam String token) {
        boolean confirmado = usuarioService.confirmarRegistro(token); // Confirmar el registro con el token

        if (confirmado) {
            return new ResponseEntity<>("Registro confirmado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Token inválido o expirado", HttpStatus.BAD_REQUEST);
        }
    }
}
