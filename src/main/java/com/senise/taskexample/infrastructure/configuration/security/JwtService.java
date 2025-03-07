package com.senise.taskexample.infrastructure.configuration.security;

import com.senise.taskexample.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String extractEmail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (JwtException e) {
            throw new IllegalArgumentException("El token JWT es inválido", e);
        }
    }

    private SecretKey getSigningKey() {
        try {
            byte[] bytes = Decoders.BASE64.decode(secret);
            if (bytes.length < 32) {
                throw new IllegalArgumentException("La clave secreta debe tener al menos 256 bits");
            }
            return Keys.hmacShaKeyFor(bytes);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error al decodificar la clave secreta JWT", e);
        }
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        try {
            final String email = extractEmail(jwtToken);
            return email.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
        } catch (JwtException | IllegalArgumentException e) {
            return false; // Token no válido
        }
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    public String extractRole(String jwtToken) {
        Claims claims = extractAllClaims(jwtToken);
        return claims.get("role", String.class); // Extraer el rol como string
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().getName()); // Agrega el rol como un claim
        return createToken(claims, user.getEmail());
    }

    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)                // Agrega los claims personalizados
                .setSubject(email)                // Establece el subject como el email del usuario
                .setIssuedAt(new Date())          // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expira en 10 horas
                .signWith(getSigningKey())        // Firma con la clave secreta
                .compact();                       // Genera el token JWT
    }
}
