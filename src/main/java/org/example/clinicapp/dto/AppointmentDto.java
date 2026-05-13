package org.example.clinicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class AppointmentDto {
    private Long id;
    private String doctorName;
    private LocalDateTime date;
}
