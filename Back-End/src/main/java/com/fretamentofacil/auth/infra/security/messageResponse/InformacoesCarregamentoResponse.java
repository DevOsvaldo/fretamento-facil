package com.fretamentofacil.auth.infra.security.messageResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fretamentofacil.auth.domain.model.Carga;
import com.fretamentofacil.auth.domain.model.Condutor;
import lombok.*;

import java.util.List;
@Getter
@Setter

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InformacoesCarregamentoResponse {


    private List<InformacoesCargaCondutorResponse> informacoes;

    public InformacoesCarregamentoResponse(List<InformacoesCargaCondutorResponse> informacoes) {
        this.informacoes = informacoes;
    }

    public List<InformacoesCargaCondutorResponse> getInformacoes() {
        return informacoes;
    }

    public void setInformacoes(List<InformacoesCargaCondutorResponse> informacoes) {
        this.informacoes = informacoes;
    }
}

