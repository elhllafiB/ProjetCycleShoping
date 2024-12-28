package com.example.register.AllSecurity.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity

@Setter
public class JwtT {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;


    private String token;
    private boolean loggeout;

    @ManyToOne

    private Utilisateur utilisateur;

}
