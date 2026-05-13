package org.example.clinicapp.service;

import org.example.clinicapp.dto.*;

import java.util.List;

public interface AppointmentService {
    List<PatientDto> getPatientsByDoctor(Long doctorId);
    List<AppointmentDto> getAppointmentsByUser(Long userId);
    List<DoctorDto> getAllDoctors();
    MedBookDto getMedBook(Long userId);
    void saveMedBook(Long userId, String medBook);
    void createAppointment(Long patientId, CreateAppointmentRequest request);
}
