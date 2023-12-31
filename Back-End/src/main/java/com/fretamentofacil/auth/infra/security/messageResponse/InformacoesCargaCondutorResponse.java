package com.fretamentofacil.auth.infra.security.messageResponse;

import com.fretamentofacil.auth.domain.model.Carga;
import com.fretamentofacil.auth.domain.model.Condutor;

public class InformacoesCargaCondutorResponse {
    private Carga carga;
    private Condutor condutor;

    public Carga getCarga() {
        return carga;
    }

    public void setCarga(Carga carga) {
        this.carga = carga;
    }

    public Condutor getCondutor() {
        return condutor;
    }

    public void setCondutor(Condutor condutor) {
        this.condutor = condutor;
    }
}
