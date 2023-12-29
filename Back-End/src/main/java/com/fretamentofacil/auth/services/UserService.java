package com.fretamentofacil.auth.services;

import com.fretamentofacil.auth.repositories.UserRepository;
import com.fretamentofacil.auth.domain.user.User;
import org.springframework.stereotype.Service;

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
