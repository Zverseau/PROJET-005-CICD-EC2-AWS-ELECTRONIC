package com.electronic.electronic.filter;

import com.electronic.electronic.config.JwtUtils;
import com.electronic.electronic.services.serviceImpl.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;

    public JwtFilter(CustomUserDetailsService customUserDetailsService, JwtUtils jwtUtils) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Log pour déboguer
        logger.debug("Requête sur: {}", request.getRequestURI());
        logger.debug("Auth Header: {}", authHeader != null ? "présent" : "absent");

        // Si pas de header ou pas le bon format, on continue
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug("Pas de token Bearer dans la requête");
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        logger.debug("Token extrait: {}...", jwt.substring(0, Math.min(20, jwt.length())));

        // Vérifier le format du token
        if (!jwtUtils.isValidFormat(jwt)) {
            logger.error("Format de token invalide");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"erreur\": \"Format de token invalide\"}");
            return;
        }

        String userEmail = null;

        try {
            userEmail = jwtUtils.extractEmail(jwt);
            logger.debug("Email extrait du token: {}", userEmail);
        } catch (ExpiredJwtException e) {
            logger.error("Token expiré");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"erreur\": \"Token expiré\"}");
            return;
        } catch (SignatureException e) {
            logger.error("Signature invalide");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"erreur\": \"Signature token invalide\"}");
            return;
        } catch (MalformedJwtException e) {
            logger.error("Token malformé: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"erreur\": \"Token malformé\"}");
            return;
        } catch (Exception e) {
            logger.error("Erreur lors de l'extraction de l'email: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"erreur\": \"Token invalide\"}");
            return;
        }

        // Si on a un email et que l'utilisateur n'est pas déjà authentifié
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

                if (jwtUtils.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.info("Utilisateur authentifié: {}", userEmail);
                } else {
                    logger.warn("Token invalide pour: {}", userEmail);
                }
            } catch (UsernameNotFoundException e) {
                logger.error("Utilisateur non trouvé: {}", userEmail);
            }
        }

        filterChain.doFilter(request, response);
    }
}