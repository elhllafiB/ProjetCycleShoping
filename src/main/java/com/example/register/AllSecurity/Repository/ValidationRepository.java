package com.example.register.AllSecurity.Repository;


import com.example.register.AllSecurity.Entity.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ValidationRepository extends JpaRepository<Validation, Integer> {

    Optional<Validation> findBycode(String code );
}
