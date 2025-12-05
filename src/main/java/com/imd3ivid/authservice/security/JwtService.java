package com.imd3ivid.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Servicio responsable de:
 *   - Generar tokens JWT
 *   - Extraer datos del token
 *   - Validar el token
 */
@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expiration}")
    private long jwtExpirationMs;

    /**
     * Genera un token JWT usando el username como "subject".
     */
    public String generateToken(String username) {

        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)                // usuario al que pertenece el token
                .setIssuedAt(ahora)                  // fecha de creación
                .setExpiration(expiracion)           // fecha de expiración
                .signWith(
                        Keys.hmacShaKeyFor(secretKey.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    /**
     * Extrae el username (subject) a partir del token.
     */
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Comprueba si el token es válido para ese usuario:
     *  - que el username coincida
     *  - que no esté expirado
     */
    public boolean isTokenValid(String token, String username) {
        String usernameToken = extractUsername(token);
        return usernameToken.equals(username) && !isTokenExpired(token);
    }

    /**
     * Devuelve true si el token ya ha caducado.
     */
    private boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    /**
     * Obtiene todos los "claims" (datos) del token.
     */
    private Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}