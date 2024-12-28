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

public class Jwt {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;


    private boolean desactiver ;

    private boolean expirer ;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.MERGE})
    @JoinColumn(name = "Utilisateur_id")
    private Utilisateur utilisateur;
    private  String valeur;

    public Jwt(boolean desactiver, boolean expirer, Utilisateur utilisateur , String Valeur) {
        this.desactiver = desactiver;
        this.expirer = expirer;
        this.utilisateur = utilisateur;
        this.valeur = Valeur;
    }
}

