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

        // Habilitar CORS y desactivar CSRF para API REST + JWT
        http.cors(cors -> {});
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                // 🔓 Endpoints públicos (sin autenticación)
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // 🌎 GET públicos para formularios (registro, etc.)
                .requestMatchers(HttpMethod.GET, "/api/regiones/**", "/api/comunas/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()

                // 👑 Rutas solo ADMIN (crear/editar/borrar)
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/empleados/**").hasRole("ADMIN")
                .requestMatchers("/api/compras/**").hasRole("ADMIN")
                .requestMatchers("/api/detalle-compra/**").hasRole("ADMIN")
                .requestMatchers("/api/detalle-producto/**").hasRole("ADMIN")
                .requestMatchers("/api/regiones/**").hasRole("ADMIN") // POST/PUT/DELETE regiones

                // 💵 Ventas, envíos y detalle de venta: ADMIN o EMPLEADO
                .requestMatchers("/api/ventas/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers("/api/envios/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers("/api/detalle-venta/**").hasAnyRole("ADMIN", "EMPLEADO")

                // 👥 Rutas disponibles para cualquier usuario autenticado (CLIENTE / EMPLEADO / ADMIN)
                .requestMatchers("/api/clientes/**").authenticated()
                .requestMatchers("/api/carritos/**").authenticated()
                .requestMatchers("/api/detalle-carrito/**").authenticated()
                .requestMatchers("/api/comunas/**").authenticated() // POST/PUT/DELETE comunas

                // Cualquier otra request requiere estar autenticado
                .anyRequest().authenticated()
        );

        // Filtro JWT antes del UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
