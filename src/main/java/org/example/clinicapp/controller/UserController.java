package org.example.clinicapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.clinicapp.entity.User;
import org.example.clinicapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/medical")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "fullname", user.getFullName(),
                "email", user.getEmail(),
                "role", user.getRoleUser()
        ));
    }



}
