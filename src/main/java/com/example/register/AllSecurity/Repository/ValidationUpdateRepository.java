package com.example.register.AllSecurity.Repository;


import com.example.register.AllSecurity.Entity.ValisationUpdatePassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ValidationUpdateRepository extends JpaRepository<ValisationUpdatePassword,Integer > {
    Optional<ValisationUpdatePassword> findBycode(String code );
}
