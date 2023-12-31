package com.fretamentofacil.auth.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Carga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCliente;
    private String enderecoCliente;
    private Double pesoCarga;
    private Double valorFrete;
    private String tipoVeiculo;

    @Enumerated(EnumType.STRING)
    private SituacaoCarga situacaoCarga;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "gestor_Id")
    private Gestor gestor;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "condutor_Id")
    private Condutor condutor;


    public Long getCondutorId() {
        if (condutor != null) {
            return condutor.getId();
        }
        return null; // Ou uma alternativa, como -1L ou outro valor representativo de "não encontrado"
    }
}
