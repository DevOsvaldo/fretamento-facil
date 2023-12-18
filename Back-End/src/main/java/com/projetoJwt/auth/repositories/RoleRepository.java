package com.projetoJwt.auth.repositories;

import com.projetoJwt.auth.domain.user.Role;
import com.projetoJwt.auth.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(UserRole roleName);
}
