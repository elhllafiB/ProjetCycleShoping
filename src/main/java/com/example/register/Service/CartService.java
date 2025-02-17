package com.example.register.Service;

import com.example.register.AllSecurity.Service.JwtService;
import com.example.register.Entity.Cart;
import com.example.register.Repository.CartItemRepository;
import com.example.register.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.math.BigDecimal;
import com.example.register.Entity.CartItem;
@Service

public class CartService {


    @Autowired
    private  CartRepository cartRepository;
    @Autowired
    private  CartItemRepository cartItemRepository;




    public Cart getCart(Long cartId) {
        Cart cart =  this.cartRepository.findById(cartId).orElseThrow(()->new RuntimeException("the cart not found"));
        BigDecimal totalAMount = cart.getTotalAmount();
        cart.setTotalAmount(totalAMount);
        return cartRepository.save(cart);

    }




    @Transactional
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCart(id);
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);

    }


    public BigDecimal getTotalAmount(Long cartId) {

        Cart cart = this.getCart(cartId);
        BigDecimal total = cart.getItems().stream()
                .map(item->item.getTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total;
    }


    public Cart getCartByUserId(int userId) {
        return this.cartRepository.findByUserId(userId).orElseThrow(()->new RuntimeException("the cart not found"));
    }

}
