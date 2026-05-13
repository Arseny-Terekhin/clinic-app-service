package org.example.clinicapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequest {

    @NotNull(message = "username должен быть заполнен")
    @Email(message = "не правильно введенна почта")
    private String username;

    @NotNull(message = "password должен быть заполнен")
    private String password;


}
