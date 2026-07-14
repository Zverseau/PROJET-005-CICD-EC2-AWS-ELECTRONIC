package com.electronic.electronic.services.serviceImpl;


import com.electronic.electronic.models.UserBase;
import com.electronic.electronic.repositories.AdminRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserBase user = adminRepository.findByEmail(email).orElse(null);

        if (user == null) {
            System.out.println(" Utilisateur non trouvé : " + email);
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + email);
        }

        // Vérifie quel rôle est attribué
        System.out.println(" Chargement de l'utilisateur : " + user.getEmail() + " | Role: " + user.getRole().name());

        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

}