package org.example.clinicapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "appointment")
@ToString
public class Appointment {

    @Id
    @Column(name = "appointment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDateTime dateAppointment;

    @ManyToOne
    @JoinColumn(name = "patient_user_id")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "doctor_user_id")
    private User doctor;




}
