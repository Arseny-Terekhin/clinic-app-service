package org.example.clinicapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class CreateAppointmentRequest {
    private Long doctorId;
    private LocalDate date;
    private LocalTime time;
}
