package com.electronic.electronic.config;


import com.electronic.electronic.filter.JwtFilter;
import com.electronic.electronic.services.serviceImpl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(SecurityConfig.class);
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/produits/getAll",
                                "/api/v1/produits/getById/**",
                                "/api/v1/categories/getAll",
                                "/api/v1/categories/getById/**",
                                "/api/v1/commandes/commander",
                                "/api/v1/authentification/register/**",
                                "/api/v1/authentification/login/**",
                                "/api/v1/authentification/logout",
                                "/api/v1/produits/uploads/produits/**"
                        ).permitAll()

                        .requestMatchers(
                                "/api/v1/produits/create",
                                "/api/v1/produits/update/**",
                                "/api/v1/produits/delete/**",
                                "/api/v1/categories/create",
                                "/api/v1/categories/update/**",
                                "/api/v1/categories/delete/**",
                                "/api/v1/commandes/getAll",
                                "/api/v1/commandes/en-attente",
                                "/api/v1/commandes/**",
                                "/api/v1/statistiques/**"
                        ).hasRole("ADMIN")

                        .anyRequest().denyAll()
                )
                .exceptionHandling(e -> {
                    e.accessDeniedHandler((request, response, accessDeniedException) -> {
                        logger.warn("🚨 Accès refusé - Rôle ADMIN requis : {}", request.getRequestURI());
                        response.setStatus(403);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\": \"Accès réservé aux administrateurs\"}");
                    });

                    e.authenticationEntryPoint((request, response, authException) -> {
                        logger.warn("🔒 Authentification requise : {}", request.getRequestURI());
                        response.setStatus(401);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\": \"Authentification requise\"}");
                    });
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}