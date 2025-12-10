package com.duoc.AlphaSquad.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CORS + sin CSRF porque es API REST con JWT
        http.cors(cors -> {});
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                // ===== RUTAS PÚBLICAS =====
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // GET públicos para formularios (regiones, comunas, productos, etc.)
                .requestMatchers(HttpMethod.GET, "/api/regiones/**", "/api/comunas/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()

                // ===== CLIENTE / CARRITO (relajado para tu proyecto) =====
                // El control real de "debes estar logueado para comprar"
                // lo haces en el frontend (Carrito.jsx con isLoggedIn).
                .requestMatchers("/api/clientes/**").permitAll()
                .requestMatchers("/api/carritos/**").permitAll()
                .requestMatchers("/api/detalle-carrito/**").permitAll()

                // ===== ENVIOS =====
                // POST: lo puede hacer cualquiera (el front solo lo llama si el cliente está logueado)
                .requestMatchers(HttpMethod.POST, "/api/envios/**").permitAll()
                // GET/PUT/DELETE envíos: solo ADMIN o EMPLEADO
                .requestMatchers("/api/envios/**").hasAnyRole("ADMIN", "EMPLEADO")

                // ===== ZONA ADMIN =====
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/empleados/**").hasRole("ADMIN")
                .requestMatchers("/api/compras/**").hasRole("ADMIN")
                .requestMatchers("/api/detalle-compra/**").hasRole("ADMIN")
                .requestMatchers("/api/detalle-producto/**").hasRole("ADMIN")
                .requestMatchers("/api/regiones/**").hasRole("ADMIN")  // POST/PUT/DELETE regiones

                // Ventas y detalle venta: ADMIN o EMPLEADO
                .requestMatchers("/api/ventas/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers("/api/detalle-venta/**").hasAnyRole("ADMIN", "EMPLEADO")

                // Cualquier otra cosa → requiere estar autenticado
                .anyRequest().authenticated()
        );

        // Filtro JWT antes del username/password
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
