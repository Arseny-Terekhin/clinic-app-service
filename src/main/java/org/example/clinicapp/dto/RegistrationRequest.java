package org.example.clinicapp.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {

    @NotNull(message = "fullName должен быть заполнен")
    private String fullName;

    @NotNull(message = "email должен быть заполнен")
    @Email(message = "не правильно введенна почта")
    private String email;

    @NotNull(message = "password должен быть заполнен")
    private String password;

}
