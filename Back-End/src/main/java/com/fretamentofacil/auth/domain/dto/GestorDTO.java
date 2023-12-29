package com.fretamentofacil.auth.domain.dto;

import com.fretamentofacil.auth.domain.user.UserRole;

public record GestorDTO(String login, String password, String nome, String cpf,String cargo, UserRole role){

}