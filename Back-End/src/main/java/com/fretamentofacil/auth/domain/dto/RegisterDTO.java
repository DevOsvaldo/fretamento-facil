package com.fretamentofacil.auth.domain.dto;

import com.fretamentofacil.auth.domain.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
