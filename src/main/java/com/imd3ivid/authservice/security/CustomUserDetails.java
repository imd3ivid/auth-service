package com.imd3ivid.authservice.security;

import com.imd3ivid.authservice.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Implementación de UserDetails que envuelve nuestra entidad User.
 *
 * Spring Security no sabe nada de nuestra clase User,
 * pero sí sabe manejar objetos UserDetails.
 */
public class CustomUserDetails implements UserDetails {

    private final User user;

    // Constructor: guardamos el usuario interno
    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * Devuelve la lista de roles/permisos del usuario.
     *
     * En nuestro caso el User solo tiene un campo "role" (String),
     * por ejemplo "ROLE_USER" o "ROLE_ADMIN".
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convertimos el String role en un SimpleGrantedAuthority
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    // Password encriptada almacenada en BD
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // Username (nuestro campo username)
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // A partir de aquí devolvemos true para simplificar.
    // Más adelante podríamos usar campos extra (accountNonExpired, etc.).

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Usamos el campo enabled de nuestra entidad
        return user.isEnabled();
    }
}
