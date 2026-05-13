package org.example.clinicapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.clinicapp.dto.*;
import org.example.clinicapp.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medical/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final AppointmentService appointmentService;

    @GetMapping("/patients/{doctorId}")
    public ResponseEntity<List<PatientDto>> getPatients(@PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.getPatientsByDoctor(doctorId));
    }

    @GetMapping("/medbook/user/{userId}")
    public ResponseEntity<MedBookDto> getMedBook(@PathVariable Long userId) {
        return ResponseEntity.ok(appointmentService.getMedBook(userId));
    }

    @PutMapping("/medbook/user/{userId}")
    public ResponseEntity<?> saveMedBook(@PathVariable Long userId,
                                         @RequestBody MedBookDto dto) {
        appointmentService.saveMedBook(userId, dto.getMedBook());
        return ResponseEntity.ok(Map.of("message", "Медкнижка сохранена"));
    }
}
