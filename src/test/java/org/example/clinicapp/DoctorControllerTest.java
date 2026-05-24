package org.example.clinicapp;

import org.example.clinicapp.controller.DoctorController;
import org.example.clinicapp.dto.MedBookDto;
import org.example.clinicapp.dto.PatientDto;
import org.example.clinicapp.service.AppointmentService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DoctorControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private DoctorController doctorController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getPatients_returnsListOfPatients() throws Exception {
        PatientDto patient = new PatientDto();
        when(appointmentService.getPatientsByDoctor(5L)).thenReturn(List.of(patient));

        mockMvc.perform(get("/medical/doctor/patients/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        verify(appointmentService).getPatientsByDoctor(5L);
    }

    @Test
    void getPatients_emptyList_returnsEmptyArray() throws Exception {
        when(appointmentService.getPatientsByDoctor(99L)).thenReturn(List.of());

        mockMvc.perform(get("/medical/doctor/patients/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getMedBook_returnsMedBookDto() throws Exception {
        MedBookDto dto = new MedBookDto();
        dto.setMedBook("Диагноз: здоров");
        when(appointmentService.getMedBook(1L)).thenReturn(dto);

        mockMvc.perform(get("/medical/doctor/medbook/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medBook").value("Диагноз: здоров"));

        verify(appointmentService).getMedBook(1L);
    }

    @Test
    void saveMedBook_validRequest_returns200WithMessage() throws Exception {
        MedBookDto dto = new MedBookDto();
        dto.setMedBook("Новая запись");
        doNothing().when(appointmentService).saveMedBook(eq(1L), eq("Новая запись"));

        mockMvc.perform(put("/medical/doctor/medbook/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Медкнижка сохранена"));

        verify(appointmentService).saveMedBook(1L, "Новая запись");
    }
}