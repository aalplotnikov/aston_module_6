package com.aalplotnikov.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Данные для создания или обновления пользователя")
public record UserRequestDTO(
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
        int age
) {
}
