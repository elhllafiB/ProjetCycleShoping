package com.example.register.Repository;

import com.example.register.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT ci FROM Cart ci WHERE ci.user.id = :userId")
    Optional<Cart> findByUserId(@Param("userId") int userId);
}
