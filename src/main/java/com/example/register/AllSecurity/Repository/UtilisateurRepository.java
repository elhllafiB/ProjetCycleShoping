package com.example.register.AllSecurity.Repository;

import com.example.register.AllSecurity.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    //Si l'utilisateur n'existe pas : Elle retourne un Optional.empty() pour éviter les NullPointerException.
    Optional<Utilisateur> findByEmail(String email);
}
