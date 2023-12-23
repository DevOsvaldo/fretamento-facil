package com.projetoJwt.auth.domain.dto;

import com.projetoJwt.auth.domain.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
