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

    private final UsuarioRepositorio usuarioRepositorio;
    private final JavaMailSender mailSender;

    @Autowired
    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, JavaMailSender mailSender) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.mailSender = mailSender;
    }

    public void registrarUsuario(String nombreCompleto, String correo, String telefono, String codigoPostal, String contrasena) {
        String hashedPassword = BCrypt.hashpw(contrasena, BCrypt.gensalt());
        Usuario usuario = new Usuario(nombreCompleto, correo, telefono, codigoPostal, hashedPassword);
        usuarioRepositorio.save(usuario);
    }

    public boolean compararContrasena(String contrasena, String contrasenaHasheada) {
        return BCrypt.checkpw(contrasena, contrasenaHasheada);
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo);
    }

    public boolean enviarTokenRecuperacion(String correo) {
        Usuario usuario = usuarioRepositorio.findByCorreo(correo);
        if (usuario == null) {
            return false;
        }

        String token = UUID.randomUUID().toString();
        usuario.setTokenRecuperacion(token);
        usuario.setTokenExpiracion(System.currentTimeMillis() + 3600000); // 1 hora
        usuarioRepositorio.save(usuario);

        // Enviar correo de recuperación
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(correo);
        message.setSubject("Recuperación de contraseña");
        message.setText("Haz clic en el siguiente enlace para restablecer tu contraseña:\n" +
                "http://localhost:9094/usuarios/restablecer?token=" + token);
        mailSender.send(message);

        return true;
    }

    public Usuario buscarUsuarioPorToken(String token) {
        return usuarioRepositorio.findByTokenRecuperacion(token);
    }

    public void restablecerContrasena(Usuario usuario, String nuevaContrasena) {
        // Asegúrate de que la nueva contraseña se cifre antes de guardarla
        String hashedPassword = BCrypt.hashpw(nuevaContrasena, BCrypt.gensalt());
        usuario.setContrasena(hashedPassword);
        usuarioRepositorio.save(usuario);
    }

    public boolean confirmarRegistro(String token) {
        Usuario usuario = usuarioRepositorio.findByTokenConfirmacion(token);
        if (usuario == null || usuario.getTokenExpiracion() < System.currentTimeMillis()) {
            return false;
        }

        usuario.setConfirmado(true);  // Confirmación del registro
        usuarioRepositorio.save(usuario);
        return true;
    }
}
