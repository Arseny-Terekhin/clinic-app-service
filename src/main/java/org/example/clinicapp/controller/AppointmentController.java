package org.example.clinicapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.clinicapp.dto.AppointmentDto;
import org.example.clinicapp.dto.CreateAppointmentRequest;
import org.example.clinicapp.dto.DoctorDto;
import org.example.clinicapp.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medical/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/my/{userId}")
    public ResponseEntity<List<AppointmentDto>> getMyAppointments(@PathVariable Long userId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByUser(userId));
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDto>> getDoctors() {
        return ResponseEntity.ok(appointmentService.getAllDoctors());
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestParam Long patientId,
                                               @RequestBody CreateAppointmentRequest request) {
        appointmentService.createAppointment(patientId, request);
        return ResponseEntity.ok(Map.of("message", "Запись создана"));
    }
}
