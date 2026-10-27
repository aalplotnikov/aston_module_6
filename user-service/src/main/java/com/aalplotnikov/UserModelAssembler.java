package com.aalplotnikov;

import com.aalplotnikov.DTO.UserResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserResponseDTO, EntityModel<UserResponseDTO>> {

    @Override
    public EntityModel<UserResponseDTO> toModel(UserResponseDTO user) {
        Long id = user.id();
        return EntityModel.of(
                user,
                linkTo(methodOn(UserController.class)
                        .get(id))
                        .withSelfRel(),

                linkTo(methodOn(UserController.class)
                        .update(id, null))
                        .withRel("update"),

                linkTo(methodOn(UserController.class)
                        .delete(id))
                        .withRel("delete")
        );
    }
}
