package com.projetoJwt.auth.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "Administrador")
public class Gestor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cargo;

    @JsonIgnore
    @OneToMany(mappedBy = "gestor", cascade = CascadeType.ALL)
    private List<Carga> cargas;

    public void setCargas(List<Carga> cargas){
        this.cargas = cargas;
        if(cargas != null){
            for (Carga carga : cargas){
                carga.setGestor(this);
            }
        }
    }
}
