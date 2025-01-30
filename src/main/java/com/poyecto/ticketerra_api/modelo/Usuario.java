package com.poyecto.ticketerra_api.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String codigoPostal;
    private String contrasena;

    @Column(name = "token_recuperacion")
    private String tokenRecuperacion;

    @Column(name = "token_expiracion")
    private long tokenExpiracion;

    @Column(name = "activo")
    private Boolean activo;  // Cambiado de boolean primitivo a Boolean

    @Column(name = "token_confirmacion")
    private String tokenConfirmacion;

    @Column(name = "confirmado")
    private Boolean confirmado;  // Cambiado de boolean primitivo a Boolean

    // Constructor por defecto (sin parámetros)
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(String nombreCompleto, String correo, String telefono, String codigoPostal, String contrasena) {
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.codigoPostal = codigoPostal;
        this.contrasena = contrasena;
        this.activo = false;  // Por defecto el usuario no está activo
        this.confirmado = false; // Por defecto el usuario no está confirmado
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTokenRecuperacion() {
        return tokenRecuperacion;
    }

    public void setTokenRecuperacion(String tokenRecuperacion) {
        this.tokenRecuperacion = tokenRecuperacion;
    }

    public long getTokenExpiracion() {
        return tokenExpiracion;
    }

    public void setTokenExpiracion(long tokenExpiracion) {
        this.tokenExpiracion = tokenExpiracion;
    }

    public Boolean getActivo() {
        return activo; 
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getTokenConfirmacion() {
        return tokenConfirmacion;
    }

    public void setTokenConfirmacion(String tokenConfirmacion) {
        this.tokenConfirmacion = tokenConfirmacion;
    }

    public Boolean getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(Boolean confirmado) {
        this.confirmado = confirmado;
    }
}
