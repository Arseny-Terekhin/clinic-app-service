package org.example.clinicapp;

import org.example.clinicapp.controller.AuthController;
import org.example.clinicapp.dto.AuthRequest;
import org.example.clinicapp.dto.RegistrationRequest;
import org.example.clinicapp.entity.User;
import org.example.clinicapp.service.MailSenderService;
import org.example.clinicapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private MailSenderService mailSenderService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void register_validRequest_returns200AndSendsEmail() throws Exception {
        RegistrationRequest req = new RegistrationRequest();
        req.setEmail("test@mail.ru");
        req.setPassword("password123");
        req.setFullName("Иван Иванов");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@mail.ru");

        when(userService.register(any(RegistrationRequest.class))).thenReturn(user);
        doNothing().when(mailSenderService).sendMessage(any());

        mockMvc.perform(post("/medical/regist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Пользователь зарегистрирован"))
                .andExpect(jsonPath("$.username").value("test@mail.ru"));

        verify(userService).register(any(RegistrationRequest.class));
        verify(mailSenderService).sendMessage(any());
    }

    @Test
    void login_validCredentials_returns200WithUserId() throws Exception {
        AuthRequest req = new AuthRequest();
        req.setUsername("test@mail.ru");
        req.setPassword("password123");

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("test@mail.ru");

        User user = new User();
        user.setId(42L);
        user.setEmail("test@mail.ru");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);
        when(userService.findByEmail("test@mail.ru")).thenReturn(user);

        mockMvc.perform(post("/medical/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Успешный вход"))
                .andExpect(jsonPath("$.userId").value(42));
    }

    @Test
    void login_wrongCredentials_returns401() throws Exception {
        AuthRequest req = new AuthRequest();
        req.setUsername("bad@mail.ru");
        req.setPassword("wrongpass");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/medical/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("BadCredentialsException"));
    }
}
