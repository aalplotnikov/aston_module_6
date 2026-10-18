package com.aalplotnikov.DTO;

import java.time.LocalDateTime;

public record NotificationRequestDTO(
        String email,
        Operation operation
){}
