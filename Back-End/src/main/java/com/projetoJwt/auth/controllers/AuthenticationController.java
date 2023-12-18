package com.projetoJwt.auth.controllers;
import com.projetoJwt.auth.domain.model.Carga;
import com.projetoJwt.auth.domain.user.*;
import com.projetoJwt.auth.infra.security.TokenService;

import com.projetoJwt.auth.infra.security.messageResponse.MessageResponse;
import com.projetoJwt.auth.repositories.RoleRepository;
import com.projetoJwt.auth.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TokenService tokenService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private void createRoleIfNotFound(UserRole role) {
        Optional<Role> optionalRole = roleRepository.findByRoleName(role);
        if (optionalRole.isEmpty()) {
            roleRepository.save(new Role(role));
        }
    }
    @GetMapping("/login")
    @ResponseStatus(code = HttpStatus.OK)
    public List<User> findallUser(){
        return repository.findAll();
    }
    @PostMapping("/login")
    public ResponseEntity login( @RequestBody @Valid AuthenticationDTO data){
        LOGGER.info("Received login request for user: {}", data.login());
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        LOGGER.info("Login successful for user: {}", data.login(), data);
        return ResponseEntity.ok(new LoginResponseDTO(token)); //
    }


    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        LOGGER.info("Received registration request for user: {}, with role: {}", data.login(), data.role());

        if (repository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Nome de usuário já ocupado!"));
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        // Cria e salva as roles se não existirem
        createRoleIfNotFound(UserRole.ADMIN);
        createRoleIfNotFound(UserRole.USER);
        createRoleIfNotFound(UserRole.MOD);

        // Obtém as roles do banco de dados
        Role adminRole = roleRepository.findByRoleName(UserRole.ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: role ADMIN not found"));
        Role userRole = roleRepository.findByRoleName(UserRole.USER)
                .orElseThrow(() -> new RuntimeException("Error: role USER not found"));
        Role modRole = roleRepository.findByRoleName(UserRole.MOD)
                .orElseThrow(() -> new RuntimeException("Error: role MOD not found"));

        // Cria um conjunto de roles com base nos dados fornecidos
        Set<Role> roles = new HashSet<>();
        switch (data.role()) {
            case ADMIN:
                roles.add(adminRole);
                break;
            case MOD:
                roles.add(modRole);
                break;
            default:
                roles.add(userRole);
                break;
        }
        // Aqui, após configurar a "role", você pode adicionar outro log
        LOGGER.info("Configured role for user: {} - {}", data.login(), data.role());
        // Cria um novo usuário e associa as roles
        User newUser = new User(data.login(), encryptedPassword, data.role());
        newUser.setRoles(roles);

        // Salva o novo usuário no banco de dados
        repository.save(newUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

}



