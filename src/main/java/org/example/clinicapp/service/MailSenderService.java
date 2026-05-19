package org.example.clinicapp.service;


import org.example.clinicapp.dto.EmailMessage;

public interface MailSenderService {

    void sendMessage(EmailMessage emailMessage);
}
