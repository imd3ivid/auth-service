package com.imd3ivid.authservice.service;

import com.imd3ivid.authservice.dto.AuthResponse;
import com.imd3ivid.authservice.dto.LoginRequest;

/**
 * Servicio responsable de la lógica de autenticación (login).
 *
 * - Recibe username + password
 * - Valida credenciales
 * - Devuelve un token JWT si todo es correcto
 */
public interface AuthService {

    AuthResponse login(LoginRequest request);
}
