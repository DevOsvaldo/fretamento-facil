package com.projetoJwt.auth.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetoJwt.auth.domain.user.Role;
import com.projetoJwt.auth.domain.user.User;
import com.projetoJwt.auth.domain.user.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Entity
@Table(name = "Moderador")
public class Gestor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String cargo;

    @JsonIgnore
    @OneToMany(mappedBy = "gestor", cascade = CascadeType.ALL)
    private List<Carga> cargas;
    @JsonIgnore
    @OneToOne(mappedBy = "gestor",  cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "gestor_role",
            joinColumns = @JoinColumn(name = "Gestor_id"),
            inverseJoinColumns = @JoinColumn(name = "Role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Gestor(){}
    public Gestor(String nome, String cpf, String cargo, User user) {
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.user = user;
    }

    public void setCargas(List<Carga> cargas){
        this.cargas = cargas;
        if(cargas != null){
            for (Carga carga : cargas){
                carga.setGestor(this);
            }
        }
    }
   public void addModeradorRole() {
        roles.add(new Role(UserRole.MOD));
    }

    public Set<Role> getRoles() {

        return roles;
    }
}
