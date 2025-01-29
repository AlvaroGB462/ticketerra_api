package com.poyecto.ticketerra_api.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.poyecto.ticketerra_api.modelo.Usuario;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByTokenRecuperacion(String token);

    // Método para buscar usuario por token de confirmación
    Optional<Usuario> findByTokenConfirmacion(String token);
}
