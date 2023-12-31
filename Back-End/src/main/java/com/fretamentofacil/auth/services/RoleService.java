package com.fretamentofacil.auth.services;

import com.fretamentofacil.auth.repositories.RoleRepository;
import com.fretamentofacil.auth.domain.user.Role;
import com.fretamentofacil.auth.domain.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role getOrCreateRole(UserRole roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}

