package com.fretamentofacil.auth.domain.dto;

import com.fretamentofacil.auth.domain.model.SituacaoCarga;

public record CargaDTO(Long id, String nomeCliente, String enderecoCliente,
                       Double pesoCarga, Double valorFrete, String tipoVeiculo, SituacaoCarga situacaoCarga) {
}

