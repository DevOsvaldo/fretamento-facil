package com.projetoJwt.auth.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

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



}
