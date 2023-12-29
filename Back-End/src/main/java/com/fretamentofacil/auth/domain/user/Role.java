package com.fretamentofacil.auth.domain.user;

import com.fretamentofacil.auth.domain.model.Condutor;
import com.fretamentofacil.auth.domain.model.Gestor;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<Condutor> condutores = new HashSet<>();


    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<Gestor> gestores = new HashSet<>();

    public Role() {

    }


    public Role(UserRole roleName) {
        this.roleName = roleName;
    }


    public UserRole getRoleName() {
        return roleName;
    }
}


