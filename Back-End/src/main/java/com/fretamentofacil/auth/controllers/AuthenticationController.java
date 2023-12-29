package com.fretamentofacil.auth.controllers;
import com.fretamentofacil.auth.domain.dto.AuthenticationDTO;
import com.fretamentofacil.auth.domain.dto.LoginResponseDTO;
import com.fretamentofacil.auth.domain.dto.RegisterDTO;
import com.fretamentofacil.auth.domain.user.Role;
import com.fretamentofacil.auth.domain.user.User;
import com.fretamentofacil.auth.domain.user.UserRole;
import com.fretamentofacil.auth.infra.security.TokenService;
import com.fretamentofacil.auth.infra.security.messageResponse.MessageResponse;
import com.fretamentofacil.auth.repositories.RoleRepository;
import com.fretamentofacil.auth.repositories.UserRepository;
import com.fretamentofacil.auth.services.CondutorService;
import com.fretamentofacil.auth.domain.model.Condutor;


import com.fretamentofacil.auth.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserService service;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CondutorService condutorService;
    public static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
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
    @GetMapping("/login/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<Condutor> findUserById(@PathVariable Long id){
        User user = repository.findById(id).orElseThrow(()->new RuntimeException("Não encontrado"));
        Condutor condutor = user.getCondutor();
        LOGGER.info("Encontrado: "+user.getCondutor());
        return Optional.ofNullable(condutor);
    }
    @PostMapping("/login")
    public ResponseEntity login( @RequestBody @Valid AuthenticationDTO data){
        LOGGER.info("Recebida solicitação de login para o usuário: {}", data.login());
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());


        LOGGER.info("Login bem-sucedido para o usuário: {}", data.login(), data);

        return ResponseEntity.ok(new LoginResponseDTO(token)); //
    }


    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        LOGGER.info("Recebida solicitação de registro para o usuário: {}, com função: {}", data.login(), data.role());

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
                .orElseThrow(() -> new RuntimeException("Error: role ADMIN não encontrada"));
        Role userRole = roleRepository.findByRoleName(UserRole.USER)
                .orElseThrow(() -> new RuntimeException("Error: role USER não encontrada"));
        Role modRole = roleRepository.findByRoleName(UserRole.MOD)
                .orElseThrow(() -> new RuntimeException("Error: role MOD não encontrada"));

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
        LOGGER.info("Role configurada para o usuário: {} - {}", data.login(), data.role());
        // Cria um novo usuário e associa as roles
        User newUser = new User(data.login(), encryptedPassword, data.role());
        newUser.setRoles(roles);

        // Salva o novo usuário no banco de dados
        repository.save(newUser);


        return ResponseEntity.ok(new MessageResponse("Usuário registrado com sucesso"));
    }

    @PutMapping("/changepassword/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity updatePassword(
            @PathVariable Long id,
            @RequestParam String newPassword) {

        User user = service.findById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Atualizar Senha
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);

        LOGGER.info("Senha do usuário {} atualizada com sucesso.", user.getUsername());

        return ResponseEntity.ok(Map.of("message", "Senha atualizada com sucesso!"));
    }

}



