package com.imd3ivid.authservice.service;

import com.imd3ivid.authservice.dto.AuthResponse;
import com.imd3ivid.authservice.dto.LoginRequest;
import com.imd3ivid.authservice.entity.User;
import com.imd3ivid.authservice.repository.UserRepository;
import com.imd3ivid.authservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementación de AuthService.
 *
 * Flujo del login:
 *  1. Buscar usuario por username
 *  2. Comparar la contraseña recibida con la de BD (encriptada)
 *  3. Si todo está bien, generar un token JWT
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest request) {

        // 1. Buscar usuario por username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Validar contraseña (texto plano vs hash en BD)
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // 3. Generar token JWT usando el username
        String token = jwtService.generateToken(user.getUsername());

        // 4. Devolver token y mensaje
        return new AuthResponse(token, "Login exitoso");
    }
}
