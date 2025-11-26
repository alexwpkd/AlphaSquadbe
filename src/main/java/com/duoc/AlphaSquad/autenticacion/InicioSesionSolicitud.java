package com.duoc.AlphaSquad.autenticacion;

public class InicioSesionSolicitud {

    private String correo;
    private String password;

    public InicioSesionSolicitud() {}

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
