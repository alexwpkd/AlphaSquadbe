package com.duoc.AlphaSquad.autenticacion;

public class InicioSesionResponsivo {

    private String token;
    private String rol;

    // 👇 Nuevos campos para el front
    private Long idCliente;   // solo se llena si el usuario es CLIENTE
    private String correo;    // correo del usuario autenticado

    public InicioSesionResponsivo() {
    }

    public InicioSesionResponsivo(String token, String rol) {
        this.token = token;
        this.rol = rol;
    }

    public InicioSesionResponsivo(String token, String rol, Long idCliente, String correo) {
        this.token = token;
        this.rol = rol;
        this.idCliente = idCliente;
        this.correo = correo;
    }

    // === GETTERS / SETTERS ===

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
