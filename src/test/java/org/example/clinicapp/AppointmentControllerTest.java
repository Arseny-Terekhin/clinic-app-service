package org.example.clinicapp;

import org.example.clinicapp.controller.AppointmentController;
import org.example.clinicapp.dto.AppointmentDto;
import org.example.clinicapp.dto.CreateAppointmentRequest;
import org.example.clinicapp.dto.DoctorDto;
import org.example.clinicapp.service.AppointmentService;
import org.example.clinicapp.service.MailSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private MailSenderService mailSenderService;

    @InjectMocks
    private AppointmentController appointmentController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getMyAppointments_returnsListOfAppointments() throws Exception {
        AppointmentDto dto = new AppointmentDto();
        when(appointmentService.getAppointmentsByUser(1L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/medical/appointment/my/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        verify(appointmentService).getAppointmentsByUser(1L);
    }

    @Test
    void getMyAppointments_emptyList_returnsEmptyArray() throws Exception {
        when(appointmentService.getAppointmentsByUser(99L)).thenReturn(List.of());

        mockMvc.perform(get("/medical/appointment/my/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getDoctors_returnsListOfDoctors() throws Exception {
        DoctorDto doctor = new DoctorDto();
        when(appointmentService.getAllDoctors()).thenReturn(List.of(doctor));

        mockMvc.perform(get("/medical/appointment/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        verify(appointmentService).getAllDoctors();
    }

    @Test
    void getDoctors_emptyList_returnsEmptyArray() throws Exception {
        when(appointmentService.getAllDoctors()).thenReturn(List.of());

        mockMvc.perform(get("/medical/appointment/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void createAppointment_validRequest_returns200WithMessage() throws Exception {
        CreateAppointmentRequest request = new CreateAppointmentRequest();
        doNothing().when(appointmentService).createAppointment(eq(1L), any(CreateAppointmentRequest.class));

        mockMvc.perform(post("/medical/appointment")
                        .param("patientId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Запись создана"));

        verify(appointmentService).createAppointment(eq(1L), any(CreateAppointmentRequest.class));
    }
}
