package com.imd3ivid.authservice.controller;

import com.imd3ivid.authservice.dto.AuthResponse;
import com.imd3ivid.authservice.dto.LoginRequest;
import com.imd3ivid.authservice.dto.RegisterRequest;
import com.imd3ivid.authservice.entity.User;
import com.imd3ivid.authservice.service.AuthService;
import com.imd3ivid.authservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de autenticación:
 *  - /api/auth/register
 *  - /api/auth/login
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    /**
     * Registro de usuario.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = userService.register(request);

        AuthResponse response = new AuthResponse(
                null,
                "Usuario registrado correctamente con id: " + user.getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Login de usuario.
     *
     * Si username + password son correctos, devuelve un token JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }
}

