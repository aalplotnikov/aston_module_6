package com.aalplotnikov;

import com.aalplotnikov.DTO.NotificationRequestDTO;
import com.aalplotnikov.DTO.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private JavaMailSender mailSender;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService =
                new NotificationService(mailSender, "sender@yandex.ru");
    }

    @Test
    void shouldSendCreateEmail() {
        notificationService.notify(
                new NotificationRequestDTO("aalplotnikov@gmail.com", Operation.CREATE)
        );

        SimpleMailMessage expected = new SimpleMailMessage();
        expected.setFrom("sender@yandex.ru");
        expected.setTo("aalplotnikov@gmail.com");
        expected.setSubject("Аккаунт создан");
        expected.setText(
                "Здравствуйте! Ваш аккаунт на сайте был успешно создан"
        );

        verify(mailSender).send(expected);
    }

    @Test
    void shouldSendDeleteEmail() {
        notificationService.notify(
                new NotificationRequestDTO("aalplotnikov@gmail.com", Operation.DELETE)
        );

        SimpleMailMessage expected = new SimpleMailMessage();
        expected.setFrom("sender@yandex.ru");
        expected.setTo("aalplotnikov@gmail.com");
        expected.setSubject("Аккаунт удален");
        expected.setText("Здравствуйте! Ваш аккаунт был удалён");

        verify(mailSender).send(expected);
    }
}