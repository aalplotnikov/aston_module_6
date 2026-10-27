package com.aalplotnikov.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Данные пользователя возвращаемые в ответе API")
public record UserResponseDTO(
        @Schema(
                description = "ID пользователя",
                example = "1"
        )
        Long id,
        @Schema(
                description = "Имя пользователя",
                example = "Андрей"
        )
        String name,
        @Schema(
                description = "email пользователя",
                example = "aalplotnikov@gmail.com"
        )
        String email,
        @Schema(
                description = "Возраст пользователя",
                example = "74"
        )
        int age,
        @Schema(
                description = "Дата и время создания пользователя",
                example = "2026-10-27T22:57:46"
        )
        LocalDateTime createdAt
) {
}
