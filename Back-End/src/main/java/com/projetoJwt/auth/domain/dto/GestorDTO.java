package com.projetoJwt.auth.domain.dto;

import com.projetoJwt.auth.domain.user.UserRole;

public record GestorDTO(String login, String password, String nome, String cpf,String cargo, UserRole role){

}