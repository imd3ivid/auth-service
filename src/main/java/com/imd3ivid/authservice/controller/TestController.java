package com.imd3ivid.authservice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de prueba para comprobar que el token JWT funciona.
 */
@RestController
public class TestController {

    /**
     * Endpoint protegido.
     *
     * - SecurityConfig exige estar autenticado para acceder a /api/test/**
     * - Si enviamos un JWT válido en el header Authorization,
     *   Spring inyectará el objeto Authentication con el usuario actual.
     */
    @GetMapping("/api/test/me")
    public String me(Authentication authentication) {

        // authentication.getName() normalmente devuelve el username
        String username = authentication.getName();

        return "Hola " + username + ", estás autenticado correctamente 😎";
    }
}
