package com.projetoJwt.auth.repositories;

import com.projetoJwt.auth.domain.model.Carga;
import com.projetoJwt.auth.domain.model.SituacaoCarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CargaRepository extends JpaRepository<Carga, Long> {
    List<Carga> findBySituacaoCarga(SituacaoCarga situacaoCarga);
}
