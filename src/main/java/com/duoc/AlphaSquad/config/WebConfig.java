package com.duoc.AlphaSquad.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(
                        // Front locales
                        "http://localhost:3000",
                        "http://localhost:8080",
                        "http://localhost:5173",
                        "http://127.0.0.1:3000",
                        "http://127.0.0.1:8080",
                        "http://127.0.0.1:5173",

                        // Front sirviendo desde tu IP pública (si llegas a abrirlo así)
                        "http://23.20.26.200",        // típico puerto 80 o 8080
                        "http://23.20.26.200:5173"    // por si corres Vite apuntando a esa IP
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")          // Content-Type, Authorization, etc.
                .exposedHeaders("*")          // si quieres leer cabeceras custom
                .allowCredentials(true);      // permite Authorization: Bearer ...
    }
}
