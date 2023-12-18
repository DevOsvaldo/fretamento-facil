package com.projetoJwt.auth.domain.model;

public enum SituacaoCondutor {
    AGUARDANDO("Aguardando"),
    CARREGANDO("CARREGANDO"),
    EMVIAGEM("VIAJANDO");


    private final String descricao;

    SituacaoCondutor(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
