package com.fretamentofacil.auth.repositories;

import com.fretamentofacil.auth.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);
    Boolean existsByLogin(String login);
    Optional<User> findById(Long id);

}
