package com.fretamentofacil.auth.repositories;

import com.fretamentofacil.auth.domain.model.SituacaoCondutor;
import com.fretamentofacil.auth.domain.model.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, Long> {
    List<Condutor> findBySituacaoCondutor(SituacaoCondutor situacaoCondutor);
    List<Condutor> findByDeletedFalse();

    List<Condutor> findByDeletedTrue();
}
