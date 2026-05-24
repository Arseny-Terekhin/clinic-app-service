package org.example.clinicapp;

import org.example.clinicapp.controller.UserController;
import org.example.clinicapp.dto.enums.RoleUser;
import org.example.clinicapp.entity.User;
import org.example.clinicapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getUser_existingId_returnsUserFields() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setFullName("Иванов Иван Иванович");
        user.setEmail("ivan@mail.ru");
        user.setRoleUser(RoleUser.PATIENT);

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/medical/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullname").value("Иванов Иван Иванович"))
                .andExpect(jsonPath("$.email").value("ivan@mail.ru"))
                .andExpect(jsonPath("$.role").value("PATIENT"));

        verify(userService).findById(1L);
    }

    @Test
    void getUser_notFound_returns500() throws Exception {
        when(userService.findById(999L)).thenThrow(new RuntimeException("User not found"));

        assertThrows(Exception.class, () ->
                mockMvc.perform(get("/medical/user/999"))
        );

        verify(userService).findById(999L);
    }
}
