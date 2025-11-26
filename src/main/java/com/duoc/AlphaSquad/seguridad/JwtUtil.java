package com.duoc.AlphaSquad.seguridad;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "ALPHA_SQUAD_SECRETO_2025";

    public String generarToken(String correo, String rol) {
        return Jwts.builder()
                .claim("correo", correo)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)) // 2 horas
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public Claims obtenerClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validarToken(String token) {
        try {
            obtenerClaims(token); // Si falla, lanza excepción
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
