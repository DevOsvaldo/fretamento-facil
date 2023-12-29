package com.fretamentofacil.auth.repositories;

import com.fretamentofacil.auth.domain.user.Role;
import com.fretamentofacil.auth.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(UserRole roleName);
}
