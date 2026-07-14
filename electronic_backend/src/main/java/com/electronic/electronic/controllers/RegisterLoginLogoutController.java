package com.electronic.electronic.controllers;

import com.electronic.electronic.DTOs.Request.AdminRegisterRequestDTO;
import com.electronic.electronic.DTOs.Request.LoginRequestDTO;
import com.electronic.electronic.config.JwtUtils;
import com.electronic.electronic.mappers.RegisterMapper;
import com.electronic.electronic.models.Admin;
import com.electronic.electronic.models.Role;
import com.electronic.electronic.repositories.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/authentification")
@RequiredArgsConstructor
public class RegisterLoginLogoutController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterLoginLogoutController.class);
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RegisterMapper registerMapper;
    private final JwtUtils jwtUtils;


    // ==================== Admin ====================
    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRegisterRequestDTO request) {
        if (adminRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Cet Admin existe déjà");
        }
        Admin admin = registerMapper.toResponse(request);
        admin.setRole(Role.ADMIN);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
        return ResponseEntity.ok("Admin enregistré avec succès");
    }

    @PostMapping("/login/admin")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginRequestDTO request) {
        logger.info("Tentative connexion ADMIN: {}", request.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            Admin admin = adminRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Admin non trouvé"));

            String token = jwtUtils.generateTokenAdmin(admin);

            return ResponseEntity.ok()
                    .body(Map.of(
                            "token", token,
                            "adminId", admin.getId()
                    ));

        } catch (Exception ex) {
            logger.error("Échec connexion admin: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("erreur", "Mail ou mot de passe invalide"));
        }
    }


    //    // ==================== Logout ====================
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erreur", "Token invalide ou manquant"));
        }

        String jwt = token.substring(7);
        logger.info("Déconnexion - Token: {}", jwt.substring(0, 10) + "...");

        // Option: Invalider le token (à implémenter dans JwtUtils)
        // jwtUtils.invalidateToken(jwt);

        return ResponseEntity.ok(Map.of(
                "message", "Déconnexion réussie",
                "succes", true
        ));
    }
}
