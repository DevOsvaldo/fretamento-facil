package com.projetoJwt.auth.domain.user;



public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    MOD("ROLE_MODERADOR");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }


    }



