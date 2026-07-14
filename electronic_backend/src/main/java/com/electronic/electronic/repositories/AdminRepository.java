package com.electronic.electronic.repositories;

import com.electronic.electronic.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByEmail(String email);
    List<Admin> findByNomAdminContainingIgnoreCase(String nom);
}
