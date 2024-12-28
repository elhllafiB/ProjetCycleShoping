package com.example.register.AllSecurity.Repository;

import com.example.register.AllSecurity.Entity.Jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<Jwt,Integer> {

  Optional<Jwt> findByValeurAndDesactiverAndExpirer(String valeur, boolean desactiver, boolean expirer);

  @Query("FROM Jwt j WHERE j.expirer = :expirer AND j.desactiver = :desactiver AND j.utilisateur.email = :email")
  Optional<Jwt> findUtilisateurValidToken(String email, boolean desactiver, boolean expirer);



}
