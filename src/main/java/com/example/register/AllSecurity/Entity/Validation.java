package com.example.register.AllSecurity.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Validation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id ;
    private Instant createTime;
    private Instant expireTime;
    private Instant  activationTime;
    private String code;

    @OneToOne(cascade = CascadeType.ALL)
    private Utilisateur utilisateur;
}
