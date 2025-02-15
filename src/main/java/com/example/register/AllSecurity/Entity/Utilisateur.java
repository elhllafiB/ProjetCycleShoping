package com.example.register.AllSecurity.Entity;


import com.example.register.Entity.Cart;
import com.example.register.Entity.Product;
import com.example.register.Entity.Client_Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Getter
@Setter

public class Utilisateur implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "password")
    private String mdp;
    private String nom;
    private String prenom;
    private String email;
    //verifier si le compte est actif ou pas
    private boolean actif = false;
    @OneToOne(cascade = CascadeType.ALL)
    private  Role role;
    //chaque utilisateur peut avoir plusieur token (session)
    @OneToMany(mappedBy = "utilisateur")
    private List<JwtT> tokens;
    @OneToMany(mappedBy = "utilisateur")
    private List<ValisationUpdatePassword> valisationUpdatePassword;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Client_Order> orders;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+this.role.getLibelle()));
    }

    @Override
    public String getPassword() {
        return this.mdp;
    }

    @Override
    public String getUsername() {
        return this.email;
    }



//ca veut dire le compte est actif
    @Override
    public boolean isAccountNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.actif;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isEnabled() {
        return this.actif;
    }





}
