package com.projetoJwt.auth.services;

import com.projetoJwt.auth.domain.user.Role;
import com.projetoJwt.auth.domain.user.UserRole;
import com.projetoJwt.auth.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role getOrCreateRole(UserRole roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}

