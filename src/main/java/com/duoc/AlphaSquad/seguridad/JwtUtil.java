package com.duoc.AlphaSquad.seguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Debe ser una cadena LARGA (al menos 32 caracteres para HS256)
    private static final String SECRET = "ALPHA_SQUAD_SECRETO_2025_CLAVE_SEGURA_JWT";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generarToken(String correo, String rol) {

        Date ahora = new Date();
        Date expira = new Date(ahora.getTime() + 1000 * 60 * 60 * 2); // 2 horas

        return Jwts.builder()
                .claim("correo", correo)
                .claim("rol", rol)
                .setIssuedAt(ahora)
                .setExpiration(expira)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims obtenerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validarToken(String token) {
        try {
            Claims claims = obtenerClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
