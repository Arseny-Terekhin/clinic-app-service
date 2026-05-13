package org.example.clinicapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.clinicapp.dto.AuthRequest;
import org.example.clinicapp.dto.RegistrationRequest;
import org.example.clinicapp.entity.User;
import org.example.clinicapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/medical")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/regist")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        User user = userService.register(registrationRequest);
        return ResponseEntity.ok(Map.of(
                "message", "Пользователь зарегистрирован",
                "username", user.getEmail()
        ));
    }

    @PostMapping("/auth")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            User user = userService.findByEmail(auth.getName());

            return ResponseEntity.ok(Map.of(
                    "message", "Успешный вход",
                    "userId", user.getId()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(Map.of(
                    "error", e.getClass().getSimpleName(),
                    "message", e.getMessage()
            ));
        }
    }
}
