package com.imd3ivid.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO de respuesta cuando el usuario se autentica o se registra.
 * Aquí devolveremos el token JWT y, si queremos, información básica del usuario.
 */
@Data
@AllArgsConstructor
public class AuthResponse {

    // El token JWT que generaremos más adelante
    private String token;

    // Mensaje de ayuda para el cliente (opcional)
    private String message;
}
