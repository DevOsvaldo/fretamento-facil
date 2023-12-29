package com.fretamentofacil.auth.domain.model;

public enum SituacaoCondutor {
    AGUARDANDO("Aguardando"),
    CARREGANDO("CARREGANDO"),
    EM_VIAGEM("VIAJANDO"),
    NO_CLIENTE("NO CLIENTE");


    private final String descricao;

    SituacaoCondutor(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
