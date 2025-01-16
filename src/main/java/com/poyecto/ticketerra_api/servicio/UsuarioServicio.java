package com.poyecto.ticketerra_api.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.poyecto.ticketerra_api.modelo.Usuario;
import com.poyecto.ticketerra_api.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepository;

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

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean loginUsuario(String correo, String contrasena) {
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);
        return usuario != null && BCrypt.checkpw(contrasena, usuario.getContrasena());
    }
}