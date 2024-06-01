package com.jjsoftwares.suaacademia.dto;


public record CreateUserDTO(
        String email,
        String password,
        String name
) {
}