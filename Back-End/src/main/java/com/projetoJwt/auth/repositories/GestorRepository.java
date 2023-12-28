package com.projetoJwt.auth.repositories;

import com.projetoJwt.auth.domain.model.Gestor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GestorRepository  extends JpaRepository<Gestor, Long> {
    @Query("SELECT g.id FROM Gestor g WHERE g.user.id = :userId")
    Integer findGestorIdByUserId(@Param("userId") Long userId);
}
