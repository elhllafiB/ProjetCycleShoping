package com.example.register.Controller;
import com.example.register.Entity.CartItem;
import com.example.register.AllSecurity.Service.JwtService;
import com.example.register.Entity.Cart;
import com.example.register.Repository.CartRepository;
import com.example.register.Service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Comparator;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@NoArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
//@CrossOrigin(origins = "http://localhost:4200" ,)
//@CrossOrigin(origins = "*")
@RequestMapping("/")
public class CartController {



    @Autowired
    CartService cartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private JwtService jwtService;

//    @GetMapping("/my-cart")
//    public ResponseEntity<Object> getCart(HttpServletRequest request) {
//        try {
//             int userId = this.jwtService.UserId(request);
//             Cart cart = this.cartService.getCartByUserId(userId);
//             Long cartId    = cart.getId();
//             Cart cartR = cartService.getCart(cartId);
//            return ResponseEntity.ok( cartR);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(NOT_FOUND).body("not found cart with id " );
//        }
//    }
//
    @GetMapping("/my-cart")
    public ResponseEntity<Object> getCart(HttpServletRequest request) {
        try {
            int userId = this.jwtService.UserId(request);
            Cart cart = this.cartService.getCartByUserId(userId);
            cart.setItems(
                    cart.getItems().stream()
                            .sorted(Comparator.comparing(CartItem::getId))
                            .collect(Collectors.toSet())
            );
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }
    }





    @DeleteMapping("/delet")
    public ResponseEntity<Object> clearCart(HttpServletRequest request) {

        try{
            int userId = this.jwtService.UserId(request);
            Cart cart = this.cartService.getCartByUserId(userId);
            Long cartId    = cart.getId();
            cartService.clearCart(cartId);
            return ResponseEntity.ok( "cart deleted");

        }catch (RuntimeException e){
            return ResponseEntity.status(NOT_FOUND).body("not found cart with id"  );
        }

    }



    @GetMapping("/cart/total-price")
    public ResponseEntity<Object> getTotalAmount( HttpServletRequest request) {
        try {
            int userId = this.jwtService.UserId(request);
            Cart cart = this.cartService.getCartByUserId(userId);
            Long cartId    = cart.getId();
            BigDecimal totalPrice = cartService.getTotalAmount(cartId);
            return ResponseEntity.ok("Total Price"+ totalPrice);
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body("erreur");
        }
    }












}
