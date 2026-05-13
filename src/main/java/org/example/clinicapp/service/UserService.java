package org.example.clinicapp.service;

import org.example.clinicapp.dto.RegistrationRequest;
import org.example.clinicapp.entity.User;

public interface UserService {
    User register(RegistrationRequest registrationRequest);
    User findByEmail(String email);
    User findById(Long id);
}
