package com.duoc.AlphaSquad.Validacion;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RutValidator implements ConstraintValidator<com.duoc.AlphaSquad.Validacion.RutValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            // @NotBlank se encargará de validar que no sea null/vacío
            return true;
        }

        // Limpiar puntos y guión
        String rutLimpio = value.replace(".", "").replace("-", "");
        if (rutLimpio.length() < 8 || rutLimpio.length() > 9) {
            return false;
        }

        String numero = rutLimpio.substring(0, rutLimpio.length() - 1);
        char dv = rutLimpio.charAt(rutLimpio.length() - 1);

        try {
            int rut = Integer.parseInt(numero);
            char dvCalculado = calcularDV(rut);
            return Character.toUpperCase(dvCalculado) == Character.toUpperCase(dv);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private char calcularDV(int rut) {
        int m = 0, s = 1;
        while (rut != 0) {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
            rut /= 10;
        }
        if (s == 0) return 'K';
        return (char) (s + 47); // 1-9 -> '1'-'9', 10 -> '0'
    }
}
