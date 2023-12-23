package com.projetoJwt.auth.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return  httpSecurity.cors().and()
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/login/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/auth/edit/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/auth").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/condutor").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/condutor/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/condutor/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/situacao/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/condutor/cadastro").hasRole("MOD")
                        .requestMatchers(HttpMethod.POST, "/condutor/carregar").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/cargas").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cargas/").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/condutor/{id}").
                        hasAnyRole("MOD","ADMIN")
                        .requestMatchers(HttpMethod.POST, "/cargas/cadastro").
                        hasAnyRole("MOD","ADMIN")
                        .requestMatchers(HttpMethod.POST, "/cargas/cadastro").
                        hasAnyRole("MOD","ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/cargas/{id}").
                        hasAnyRole("MOD","ADMIN")
                        .requestMatchers(HttpMethod.GET, "/cargas/{id}").
                        hasAnyRole("MOD","ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/cargas/{id}").
                        hasAnyRole("MOD","ADMIN")
                        .requestMatchers(HttpMethod.POST, "/gestor/cadastro").
                        hasAnyRole("MOD","ADMIN")
                        .requestMatchers(HttpMethod.POST, "/gestor/inserir").
                        hasAnyRole("MOD","ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/gestor/modificar/{cargaid}").hasRole("MOD")
                        .requestMatchers(HttpMethod.DELETE, "/gestor/condutor/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/gestor/info").hasRole("MOD")
                        .requestMatchers(HttpMethod.GET, "/gestor").hasRole("MOD")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer(){
        return (web -> web.ignoring().requestMatchers("/swagger-ui/**","/v3/api-docs/**"));
    }
}
