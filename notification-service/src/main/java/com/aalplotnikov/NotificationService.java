package com.aalplotnikov;

import com.aalplotnikov.DTO.Operation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final String SUBJECT_CREATE = "Аккаунт создан";
    private final String SUBJECT_DELETE = "Аккаунт удален";
    private final String TEXT_CREATE = "Здравствуйте! Ваш аккаунт на сайте был успешно создан";
    private final String TEXT_DELETE = "Здравствуйте! Ваш аккаунт был удалён";

    private final JavaMailSender mailSender;
    private final String from;

    public NotificationService(
            JavaMailSender mailSender,
            @Value("${spring.mail.username}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    public void notify (String email, Operation operation) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email не должен быть пустым");
        }
        if (operation == null) {
            throw new IllegalArgumentException("Операция обязательна");
        }
        switch (operation) {
            case CREATE -> send(email, SUBJECT_CREATE, TEXT_CREATE);
            case DELETE -> send(email, SUBJECT_DELETE, TEXT_DELETE);
        }
    }

    private void send(String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
