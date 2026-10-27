package com.aalplotnikov;

import com.aalplotnikov.DTO.UserRequestDTO;
import com.aalplotnikov.DTO.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(
        name = "User API",
        description = " API для управления  user-service(пользователями)"
)
@RestController
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    public UserController(UserService userService, UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @Operation(
            summary = "Создать пользователя",
            description = "Создаёт нового пользователя на основе переданных данных"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Пользователь успешно создан"
    )
    @PostMapping("/save")
    public ResponseEntity<EntityModel<UserResponseDTO>> save(
            @RequestBody
            UserRequestDTO userRequestDTO
            ) {
        UserResponseDTO  userResponseDTO = userService.save(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userModelAssembler.toModel(userResponseDTO));
    }

    @Operation(
            summary = "Получить пользователя по ID",
            description = "Возвращает информацию о пользователе по его ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Пользователь успешно найден"
    )
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> get(
            @PathVariable
            Long id
    ) {
        UserResponseDTO  userResponseDTO = userService.findById(id);

//        EntityModel<UserResponseDTO> model = EntityModel.of(userResponseDTO);
//
//        model.add(
//                linkTo(methodOn(UserController.class)
//                        .get(id))
//                        .withSelfRel(),
//
//                linkTo(methodOn(UserController.class)
//                        .update(id, null))
//                        .withRel("update"),
//
//                linkTo(methodOn(UserController.class)
//                        .delete(id))
//                        .withRel("delete")
//        );



        return ResponseEntity.ok(userModelAssembler.toModel(userResponseDTO));
    }

    @Operation(
            summary = "Обновить пользователя",
            description = "Обновляет данные существующего пользователя по ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Пользователь успешно обновлен"
    )
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> update(
            @PathVariable
            Long id,
            @RequestBody
            UserRequestDTO userRequestDTO
    ) {
        UserResponseDTO  userResponseDTO = userService.update(id, userRequestDTO);
        return ResponseEntity.ok(userModelAssembler.toModel(userResponseDTO));
    }

    @Operation(
            summary = "Удалить пользователя",
            description = "Удаляет пользователя по его уникальному идентификатору"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Пользователь успешно удалён"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable
            Long id
    ) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
