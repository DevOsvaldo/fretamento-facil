package com.projetoJwt.auth.domain.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRole roleName;

    public Role() {

    }


    public Role(UserRole roleName) {
        this.roleName = roleName;
    }


    public UserRole getRoleName() {
        return roleName;
    }
}
