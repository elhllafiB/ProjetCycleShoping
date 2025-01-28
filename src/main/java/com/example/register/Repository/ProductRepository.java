package com.example.register.Repository;

import com.example.register.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("select  P from Product P WHERE P.user.id = :userId")
    List<Product> findByUserId(@Param("userId") int userId);
}
