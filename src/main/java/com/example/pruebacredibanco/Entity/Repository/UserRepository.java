package com.example.pruebacredibanco.Entity.Repository;

import com.example.pruebacredibanco.Entity.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}