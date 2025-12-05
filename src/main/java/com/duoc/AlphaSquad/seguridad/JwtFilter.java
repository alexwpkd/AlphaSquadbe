package com.duoc.AlphaSquad.seguridad;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1) Dejar pasar SIEMPRE las peticiones de preflight CORS
        //    (OPTIONS /auth/login, OPTIONS /api/..., etc.)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2) Leer el header Authorization
        String header = request.getHeader("Authorization");

        // 3) Si no hay Authorization o no es Bearer, NO devolvemos 401,
        //    simplemente dejamos que el request siga (login, swagger, GET públicos, etc.)
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {
            if (jwtUtil.validarToken(token)) {
                Claims claims = jwtUtil.obtenerClaims(token);

                String correo = claims.get("correo", String.class);
                String rol = claims.get("rol", String.class); // ADMIN, EMPLEADO, CLIENTE, etc.

                // Construimos la Authentication con el rol en formato ROLE_X
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                correo,
                                null,
                                List.of(() -> "ROLE_" + rol) // ROLE_ADMIN, ROLE_EMPLEADO...
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            // Si el token es inválido/caducado, limpiamos y seguimos SIN usuario autenticado
            SecurityContextHolder.clearContext();
        }

        // 4) Continuar con el resto de filtros / controlador
        filterChain.doFilter(request, response);
    }
}
