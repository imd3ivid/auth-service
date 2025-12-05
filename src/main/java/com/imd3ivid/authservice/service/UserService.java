package com.imd3ivid.authservice.service;

import com.imd3ivid.authservice.dto.RegisterRequest;
import com.imd3ivid.authservice.entity.User;

import java.util.Optional;

/**
 * Capa de servicio: aquí ponemos la lógica de negocio
 * relacionada con los usuarios.
 */
public interface UserService {

    /**
     * Registra un nuevo usuario en la base de datos.
     */
    User register(RegisterRequest request);

    /**
     * Busca un usuario por su username.
     */
    Optional<User> findByUsername(String username);
}
