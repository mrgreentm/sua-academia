package com.jjsoftwares.suaacademia.dto;

import com.jjsoftwares.suaacademia.entities.Role;

import java.util.List;

public record RecoveryUserDTO(
        String id,
        String email,
        String name,
        List<Role> roles

) {
}
