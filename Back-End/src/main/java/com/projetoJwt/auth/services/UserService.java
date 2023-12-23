package com.projetoJwt.auth.services;

import com.projetoJwt.auth.domain.user.User;
import com.projetoJwt.auth.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User findById(Long id){
       return userRepository.findById(id).orElseThrow(null);
    }
}
