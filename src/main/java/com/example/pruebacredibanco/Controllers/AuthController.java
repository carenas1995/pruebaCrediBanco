package com.example.pruebacredibanco.Controllers;

import com.example.pruebacredibanco.Config.JwtUtil;
import com.example.pruebacredibanco.Entity.Repository.UserRepository;
import com.example.pruebacredibanco.Entity.Models.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Credenciales inválidas"));
        }

        String token = jwtUtil.generateToken(user.getEmail(),user.getRole());
        return ResponseEntity.ok(Map.of("token", token, "email", user.getEmail(), "role", user.getRole()));
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }
}