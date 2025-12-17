package com.duoc.AlphaSquad.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // ✅ Habilitar CORS (usa el bean corsConfigurationSource de abajo)
        http.cors(cors -> {});
        // ✅ Sin CSRF porque es API REST con JWT
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth

                // ✅ MUY IMPORTANTE: permitir preflight CORS
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ===== RUTAS PÚBLICAS =====
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // GET públicos para formularios (regiones, comunas, productos, etc.)
                .requestMatchers(HttpMethod.GET, "/api/regiones/**", "/api/comunas/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()

                // ===== CLIENTE / CARRITO (relajado para tu proyecto) =====
                .requestMatchers("/api/clientes/**").permitAll()
                .requestMatchers("/api/carritos/**").permitAll()
                .requestMatchers("/api/detalle-carrito/**").permitAll()

                // ===== ENVIOS =====
                .requestMatchers(HttpMethod.POST, "/api/envios/**").permitAll()
                .requestMatchers("/api/envios/**").hasAnyRole("ADMIN", "EMPLEADO")

                // ===== ZONA ADMIN =====
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/empleados/**").hasRole("ADMIN")
                .requestMatchers("/api/compras/**").hasRole("ADMIN")
                .requestMatchers("/api/detalle-compra/**").hasRole("ADMIN")
                .requestMatchers("/api/detalle-producto/**").hasRole("ADMIN")
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

    /**
     * ✅ CORS para permitir que tu frontend (EC2) consuma el backend (EC2).
     * Ajusta aquí si cambias dominio/IP del frontend.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Tu frontend (origen) en EC2
        config.setAllowedOrigins(List.of(
                "http://54.242.202.227",
                "http://ec2-54-242-202-227.compute-1.amazonaws.com",
                "http://3.208.15.185",
                "http://ec2-3-208-15-185.compute-1.amazonaws.com"
        ));

        // Métodos permitidos (incluye OPTIONS por preflight)
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Headers permitidos (clave para JWT)
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Si alguna vez necesitas leer headers desde el cliente:
        // config.setExposedHeaders(List.of("Authorization"));

        // Con JWT en header funciona igual; lo dejo en true por si usas cookies/sesión en algún punto.
        config.setAllowCredentials(true);

        // Cache del preflight (segundos)
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
