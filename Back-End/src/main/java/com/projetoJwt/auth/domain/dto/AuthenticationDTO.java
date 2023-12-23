package com.projetoJwt.auth.domain.dto;

public record AuthenticationDTO(String login, String password, String newPassword) {


    public String getNewPassword() {
        return newPassword;
    }
}
