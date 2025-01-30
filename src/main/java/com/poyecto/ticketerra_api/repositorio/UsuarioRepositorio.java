package com.poyecto.ticketerra_api.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.poyecto.ticketerra_api.modelo.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Usuario findByCorreo(String correo);
    Usuario findByTokenRecuperacion(String token);
    Usuario findByTokenConfirmacion(String token);
}
