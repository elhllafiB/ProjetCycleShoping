package com.example.register.Repository;

import com.example.register.Entity.Client_Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Client_Order, Integer> {
}
