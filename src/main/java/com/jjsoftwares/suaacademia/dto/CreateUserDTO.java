package com.jjsoftwares.suaacademia.dto;


import com.jjsoftwares.suaacademia.enums.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CreateUserDTO(
        @NotNull(message = "email is required")
        @Email(message = "Email should be valid")
        String email,
        @NotNull(message = "password is required")
        String password,
        @NotNull(message = "name is required")
        String name,
        @NotNull(message = "role is required")
        Roles role
) {
}