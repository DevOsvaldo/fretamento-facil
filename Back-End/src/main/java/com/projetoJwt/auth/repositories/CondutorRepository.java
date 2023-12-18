package com.projetoJwt.auth.repositories;

import com.projetoJwt.auth.domain.model.Condutor;
import com.projetoJwt.auth.domain.model.SituacaoCondutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, Long> {
    List<Condutor> findBySituacaoCondutor(SituacaoCondutor situacaoCondutor);
}
