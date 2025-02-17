package com.example.register.Repository;

import com.example.register.Entity.Cart;
import com.example.register.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CartItemRepository  extends JpaRepository<CartItem, Long> {

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteAllByCart(@Param("cartId") Long cartId);
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

}



