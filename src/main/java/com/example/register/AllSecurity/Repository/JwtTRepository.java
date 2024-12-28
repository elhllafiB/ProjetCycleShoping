package com.example.register.AllSecurity.Repository;

import com.example.register.AllSecurity.Entity.JwtT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface  JwtTRepository  extends JpaRepository<JwtT,Integer> {



    // chercher les token des utilisateur qui sont conncetr
    @Query("SELECT t FROM JwtT t INNER JOIN t.utilisateur u on  t.utilisateur.id = :utilaseurId and t.loggeout = false   ")
    List<JwtT> findAllTokenByUtilisateur(Integer utilaseurId);


    Optional<JwtT> findByToken(String token);





}
