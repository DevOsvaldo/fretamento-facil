package com.fretamentofacil.auth.domain.user;

import com.fretamentofacil.auth.domain.model.Condutor;
import com.fretamentofacil.auth.domain.model.Gestor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "users")
@Entity(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = 6320892075384148825L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "condutor_id")
    private Condutor condutor;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gestor_id")
    private Gestor gestor;


    public User(String login, String password, UserRole role){
        this.login = login;
        this.password = password;
        this.role = role;

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Condutor getCondutor() {
        return condutor;
    }

    public void setCondutor(Condutor condutor) {
        this.condutor = condutor;
    }

    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }

    public Gestor getGestor() {
        return gestor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_MOD"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        } else if (this.role == UserRole.MOD) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_MOD"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        } else {

            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //IGNORE LOMBOK
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


}
