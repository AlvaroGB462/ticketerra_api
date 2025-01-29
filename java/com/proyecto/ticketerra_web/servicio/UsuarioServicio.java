package com.proyecto.ticketerra_web.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.proyecto.ticketerra_web.modelo.Usuario;

@Service
public class UsuarioServicio {

    private final String API_URL = "http://localhost:8080/api/usuarios";  // Ruta base de la API
    private final RestTemplate restTemplate;
    
    @Autowired
    private JavaMailSender mailSender;

    public UsuarioServicio(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Método para realizar el login
    public boolean loginUsuario(String correo, String contrasena) {
        try {
            // Crear un objeto Usuario con los datos del login
            Usuario usuario = new Usuario(null, correo, null, null, contrasena);
            // Realizar la llamada POST a la API de login
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL + "/login", usuario, String.class);
            return response.getStatusCode().is2xxSuccessful();  // Verifica si la respuesta es exitosa
        } catch (RestClientException e) {
            return false;  // Retorna falso si ocurre un error
        }
    }

    // Método para enviar el token de recuperación
    public boolean enviarTokenRecuperacion(String correo) {
        try {
            // Llamar al servicio del backend para que envíe el correo con el token
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL + "/olvide-contrasena?correo=" + correo, null, String.class);
            return response.getStatusCode().is2xxSuccessful();  // Verifica si el correo fue enviado con éxito
        } catch (RestClientException e) {
            return false;  // Retorna falso si ocurre un error
        }
    }

    // Método para registrar un usuario
    public void registrarUsuario(String nombreCompleto, String correo, String telefono, String codigoPostal, String contrasena) {
        // Crear un objeto Usuario
        Usuario usuario = new Usuario(nombreCompleto, correo, telefono, codigoPostal, contrasena);
        // Realizar la llamada POST para registrar al usuario en la API
        restTemplate.postForEntity(API_URL + "/registrar", usuario, String.class);
    }
}
