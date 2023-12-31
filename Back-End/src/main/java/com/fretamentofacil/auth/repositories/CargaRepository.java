package com.fretamentofacil.auth.repositories;

import com.fretamentofacil.auth.domain.model.Carga;
import com.fretamentofacil.auth.domain.model.SituacaoCarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CargaRepository extends JpaRepository<Carga, Long> {
    List<Carga> findBySituacaoCarga(SituacaoCarga situacaoCarga);

    List<Carga> findCargasByCondutor_Id(Long condutorId);
}
