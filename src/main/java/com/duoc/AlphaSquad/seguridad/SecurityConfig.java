package com.duoc.AlphaSquad.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // Controladores por rol
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/empleados/**").hasRole("ADMIN")
                .requestMatchers("/api/compras/**").hasRole("ADMIN")
                .requestMatchers("/api/detallesCompra/**").hasRole("ADMIN")
                .requestMatchers("/api/detallesProducto/**").hasRole("ADMIN")
                .requestMatchers("/api/regiones/**").hasRole("ADMIN")

                .requestMatchers("/api/productos/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers("/api/ventas/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers("/api/envios/**").hasAnyRole("ADMIN", "EMPLEADO")
                .requestMatchers("/api/detallesVenta/**").hasAnyRole("ADMIN", "EMPLEADO")

                .requestMatchers("/api/clientes/**").authenticated()
                .requestMatchers("/api/carritos/**").authenticated()
                .requestMatchers("/api/detallesCarrito/**").authenticated()

                .anyRequest().authenticated()
        );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
