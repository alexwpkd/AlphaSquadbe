package com.duoc.AlphaSquad.autenticacion;

public class ValidacionPassword {

    public static boolean esValido(String password) {

        if (password == null || password.length() < 8) return false;

        boolean mayuscula = password.matches(".*[A-Z].*");
        boolean numero = password.matches(".*[0-9].*");

        return mayuscula && numero;
    }
}
