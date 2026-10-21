package com.aalplotnikov;

import com.aalplotnikov.DTO.NotificationRequestDTO;
import com.aalplotnikov.DTO.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final String SUBJECT_CREATE = "Аккаунт создан";
    private final String SUBJECT_DELETE = "Аккаунт удален";
    private final String TEXT_CREATE = "Здравствуйте! Ваш аккаунт на сайте был успешно создан";
    private final String TEXT_DELETE = "Здравствуйте! Ваш аккаунт был удалён";

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final JavaMailSender mailSender;
    private final String from;

    public NotificationService(
            JavaMailSender mailSender,
            @Value("${spring.mail.username}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    @KafkaListener(topics = "notification-topic", groupId = "notification-consumer-group")
    public void notify (NotificationRequestDTO notificationRequestDTO) {
        if (notificationRequestDTO == null) {
            log.error("Получено пустое сообщение (null) из Kafka");
            return;
        }

        if (notificationRequestDTO.email() == null || notificationRequestDTO.email().isBlank()) {
            log.error("Критическая ошибка: Email в сообщении пустой или null. Пропускаем обработку.");
            return; // Просто выходим из метода, сдвигая оффсет
        }

        if (notificationRequestDTO.operation() == null) {
            log.error("Критическая ошибка: В сообщении отсутствует тип операции. Пропускаем обработку.");
            return;
        }

        log.info("Начало обработки уведомления для: {}, операция: {}",
                notificationRequestDTO.email(), notificationRequestDTO.operation());

        switch (notificationRequestDTO.operation()) {
            case CREATE -> send(notificationRequestDTO.email(), SUBJECT_CREATE, TEXT_CREATE);
            case DELETE -> send(notificationRequestDTO.email(), SUBJECT_DELETE, TEXT_DELETE);
            default -> log.warn("Получена операция {}, для которой нет шаблона отправки",
                    notificationRequestDTO.operation());
        }
    }

    private void send(String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);

        log.info("Соббщение на email отправлено");
    }
}
