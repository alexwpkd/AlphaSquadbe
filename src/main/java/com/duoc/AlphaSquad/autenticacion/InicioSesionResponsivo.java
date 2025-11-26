package com.duoc.AlphaSquad.autenticacion;

public class InicioSesionResponsivo {

        private String token;
        private String rol;

        public InicioSesionResponsivo(String token, String rol) {
            this.token = token;
            this.rol = rol;
        }

        public InicioSesionResponsivo() {}

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
    }
