package com.imd3ivid.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO para el login.
 * Solo necesitamos username (o email) y password.
 * De momento usaremos username + password.
 */
@Data
public class LoginRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
