package com.imd3ivid.authservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro encargado de:
 *  - Leer el token JWT del header "Authorization"
 *  - Validarlo
 *  - Poner al usuario autenticado en el SecurityContext
 *
 * Se ejecuta una vez por petición (OncePerRequestFilter).
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Leer el header Authorization
        String authHeader = request.getHeader("Authorization");

        // Si no hay header o no empieza por "Bearer ", seguimos sin tocar nada
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraer el token (quitamos el "Bearer ")
        String token = authHeader.substring(7);

        // 3. Obtener el username a partir del token
        String username = jwtService.extractUsername(token);

        // 4. Si tenemos username y el contexto aún no está autenticado...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 5. Cargar los datos del usuario desde BD
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 6. Validar el token contra ese usuario
            if (jwtService.isTokenValid(token, userDetails.getUsername())) {

                // 7. Crear el objeto de autenticación que entiende Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 8. Guardar la autenticación en el contexto
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 9. Continuar con el resto de filtros / petición
        filterChain.doFilter(request, response);
    }
}