package com.electronic.electronic.config;

import com.electronic.electronic.models.Admin;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.secret-key}")
    private String secretKey;

    @Value("${app.expirationTime}")
    private Long expirationTime;

    // Obtenir la clé de signature
    private SecretKey getSignKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Génère le token de l'Admin
    public String generateTokenAdmin(Admin admin) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("adminId", admin.getId().toString());
        claims.put("role", "ADMIN");

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(admin.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

        logger.debug("Token généré pour admin {}: {}", admin.getEmail(), token);
        return token;
    }

    // Extraire l'email du token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraire l'ID admin du token
    public String extractAdminId(String token) {
        return extractClaim(token, claims -> claims.get("adminId", String.class));
    }

    // Extraire le rôle du token
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            logger.error("Token expiré: {}", e.getMessage());
            throw e;
        } catch (SignatureException e) {
            logger.error("Signature token invalide: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            logger.error("Token malformé: {}", e.getMessage());
            logger.error("Token reçu: {}", token);
            throw e;
        } catch (Exception e) {
            logger.error("Erreur lors de l'extraction des claims: {}", e.getMessage());
            throw e;
        }
    }

    // Valider le token
    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String email = extractEmail(token);
            boolean isValid = email.equals(userDetails.getUsername()) && !isTokenExpired(token);

            if (isValid) {
                logger.debug("Token valide pour: {}", email);
            } else {
                logger.warn("Token invalide pour: {}", email);
            }

            return isValid;
        } catch (Exception e) {
            logger.error("Erreur validation token: {}", e.getMessage());
            return false;
        }
    }

    private Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    // Vérifier si le token est valide (format)
    public boolean isValidFormat(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        // Un JWT valide doit avoir 2 points (3 parties)
        long dotCount = token.chars().filter(ch -> ch == '.').count();
        if (dotCount != 2) {
            logger.warn("Token avec {} points au lieu de 2", dotCount);
            return false;
        }

        return true;
    }
}