package com.projetoJwt.auth.repositories;

import com.projetoJwt.auth.domain.model.Gestor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GestorRepository  extends JpaRepository<Gestor, Long> {
}
