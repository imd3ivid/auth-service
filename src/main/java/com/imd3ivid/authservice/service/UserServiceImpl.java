package com.imd3ivid.authservice.service;

import com.imd3ivid.authservice.dto.RegisterRequest;
import com.imd3ivid.authservice.entity.User;
import com.imd3ivid.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementación de UserService.
 *
 * @Service indica a Spring que esta clase es un "componente de servicio" y que puede inyectarse en otros sitios.
 */
@Service
@RequiredArgsConstructor // Lombok: genera un constructor con los campos final
public class UserServiceImpl implements UserService {

    // Repositorio para acceder a la BD
    private final UserRepository userRepository;

    // PasswordEncoder para encriptar la contraseña
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterRequest request) {
        // 1. Comprobamos si el username ya existe
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        // 2. Comprobamos si el email ya existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está en uso");
        }

        // 3. Encriptamos la contraseña antes de guardarla en BD
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // 4. Creamos el objeto User usando el builder de Lombok
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(hashedPassword)
                // Rol por defecto: usuario normal
                .role("ROLE_USER")
                .enabled(true)
                .build();

        // 5. Guardamos el usuario en la base de datos
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
