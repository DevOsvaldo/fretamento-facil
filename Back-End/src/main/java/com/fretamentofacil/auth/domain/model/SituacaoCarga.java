package com.fretamentofacil.auth.domain.model;

public enum SituacaoCarga {
    INATIVA("INATIVA"),
    AGUARDANDO("AGUARDANDO"),
    ATENDIDA("ATENDIDA");
    private final String descricao;

    SituacaoCarga(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
