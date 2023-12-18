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
                        .requestMatchers(HttpMethod.GET, "/auth").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/condutor").permitAll()
                        .requestMatchers(HttpMethod.GET, "/condutor/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/condutor/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/condutor/cadastro").permitAll()
                        .requestMatchers(HttpMethod.POST, "/condutor/carregar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cargas").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cargas/").permitAll()
                        .requestMatchers(HttpMethod.POST, "/cargas/cadastro").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/cargas/cadastro").hasRole("MOD")
                        .requestMatchers(HttpMethod.PUT, "/cargas/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/cargas/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/cargas/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/gestor/cadastro").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/gestor/inserir").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/gestor/modificar/{cargaid}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/gestor/condutor/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/gestor/info").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/gestor").hasRole("ADMIN")
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
