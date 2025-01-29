package com.poyecto.ticketerra_api.servicio;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.poyecto.ticketerra_api.modelo.Usuario;
import com.poyecto.ticketerra_api.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void registrarUsuario(String nombreCompleto, String correo, String telefono, String codigoPostal, String contrasena) {
        if (usuarioRepository.findByCorreo(correo).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        String contrasenaHasheada = hashPassword(contrasena);
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreCompleto(nombreCompleto);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setCodigoPostal(codigoPostal);
        nuevoUsuario.setContrasena(contrasenaHasheada);

        usuarioRepository.save(nuevoUsuario);
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean loginUsuario(String correo, String contrasena) {
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);
        return usuario != null && BCrypt.checkpw(contrasena, usuario.getContrasena());
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }

    public Usuario buscarUsuarioPorToken(String token) {
        return usuarioRepository.findByTokenRecuperacion(token).orElse(null);
    }

    private boolean enviarCorreoRecuperacion(String correo, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(correo);
            message.setSubject("Recuperación de contraseña - Ticketerra");
            message.setText("Haz clic en el siguiente enlace para recuperar tu contraseña: \n"
                    + "http://localhost:8080/api/usuarios/restablecer?token=" + token);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean enviarTokenRecuperacion(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

        if (usuario == null) {
            return false; // Si no se encuentra el usuario
        }

        String token = UUID.randomUUID().toString(); // Generar un token único

        // Guardamos el token en la base de datos
        usuario.setTokenRecuperacion(token);
        usuario.setTokenExpiracion(System.currentTimeMillis() + 3600000); // Expiración en 1 hora
        usuarioRepository.save(usuario);

        // Enviar correo con el token
        return enviarCorreoRecuperacion(correo, token);
    }


    public void actualizarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
    
 // Método para confirmar el registro
    public boolean confirmarRegistro(String token) {
        Usuario usuario = usuarioRepository.findByTokenConfirmacion(token).orElse(null);

        if (usuario != null) {
            usuario.setActivo(true); // Activar el usuario
            usuarioRepository.save(usuario); // Guardar cambios
            return true;
        }
        return false;
    }
}
