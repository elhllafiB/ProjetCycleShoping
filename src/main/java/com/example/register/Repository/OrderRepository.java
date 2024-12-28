package com.example.register.Repository;

import com.example.register.Entity.Uorder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Uorder, Integer> {
}
