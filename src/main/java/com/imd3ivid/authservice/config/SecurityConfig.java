package com.imd3ivid.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración de Spring Security.
 *
 * Aquí diremos:
 * - Qué rutas están protegidas y cuáles son públicas.
 * - Qué tipo de autenticación usamos (más adelante JWT).
 * - Qué codificador de contraseñas usaremos.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Bean que encripta las contraseñas.
     * BCrypt es el estándar actual en Spring para contraseñas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuración principal de seguridad HTTP.
     *
     * De momento vamos a dejar TODAS las rutas abiertas (permitAll)
     * para que podamos desarrollar y probar el registro sin necesidad
     * de autenticarnos.
     *
     * Más adelante:
     *  - Marcaremos /api/auth/** como públicas
     *  - El resto, protegidas con JWT
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Desactivamos CSRF porque vamos a trabajar con API REST (sin formularios HTML)
                .csrf(csrf -> csrf.disable())

                // No queremos sesiones de servidor, usaremos JWT (stateless)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Reglas de autorización
                .authorizeHttpRequests(auth -> auth
                        // Por ahora: TODO permitido
                        .anyRequest().permitAll()
                );

        // Construimos el objeto SecurityFilterChain
        return http.build();
    }
}
