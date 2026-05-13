package org.example.clinicapp.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.clinicapp.dto.*;
import org.example.clinicapp.dto.enums.RoleUser;
import org.example.clinicapp.entity.Appointment;
import org.example.clinicapp.entity.User;
import org.example.clinicapp.repository.AppointmentRepository;
import org.example.clinicapp.repository.UserRepository;
import org.example.clinicapp.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImplAppointmentService implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Override
    public List<PatientDto> getPatientsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(a -> new PatientDto(
                        a.getPatient().getId(),
                        a.getPatient().getFullName()
                ))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> getAppointmentsByUser(Long userId) {
        return appointmentRepository.findByPatientId(userId)
                .stream()
                .map(a -> new AppointmentDto(
                        a.getId(),
                        a.getDoctor().getFullName(),
                        a.getDateAppointment()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDto> getAllDoctors() {
        return userRepository.findByRoleUser(RoleUser.DOCTOR)
                .stream()
                .map(u -> new DoctorDto(u.getId(), u.getFullName()))
                .collect(Collectors.toList());
    }

    @Override
    public MedBookDto getMedBook(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return new MedBookDto(user.getMedBook(), user.getFullName());
    }

    @Override
    public void saveMedBook(Long userId, String medBook) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        user.setMedBook(medBook);
        userRepository.save(user);
    }

    @Override
    public void createAppointment(Long patientId, CreateAppointmentRequest request) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Пациент не найден"));
        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Доктор не найден"));

        LocalDateTime dateTime = LocalDateTime.of(request.getDate(), request.getTime());

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDateAppointment(dateTime);

        appointmentRepository.save(appointment);
    }
}
