package com.projetoJwt.auth.repositories;

import com.projetoJwt.auth.domain.model.Carga;
import com.projetoJwt.auth.domain.model.SituacaoCarga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CargaRepository extends JpaRepository<Carga, Long> {
    List<Carga> findBySituacaoCarga(SituacaoCarga situacaoCarga);
}
