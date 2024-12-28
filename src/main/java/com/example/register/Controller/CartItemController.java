package com.example.register.Controller;


import com.example.register.AllSecurity.Service.JwtService;
import com.example.register.Entity.Cart;
import com.example.register.Entity.CartItem;
import com.example.register.Repository.CartItemRepository;
import com.example.register.Repository.CartRepository;
import com.example.register.Service.CartItemService;
import com.example.register.Service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@NoArgsConstructor
@RestController
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    CartService cartService;
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/item/add")
    public ResponseEntity<Object> getCart(HttpServletRequest request ,
                                          @RequestParam Integer quantity ,
                                          @RequestParam  Long productId
    ) {



        try {

            int userId = this.jwtService.UserId(request);
            Cart cart = this.cartService.getCartByUserId(userId);
            Long cartId    = cart.getId();
            this.cartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok( "added successfully" );
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body("not found cart with id " );
        }
    }




    @DeleteMapping("/delete")
    public ResponseEntity<Object> supprimerItemfromCart(HttpServletRequest request ,
                                                        @RequestParam  Long productId
    ) {

        try {
            int userId = this.jwtService.UserId(request);
            Cart cart = this.cartService.getCartByUserId(userId);
            Long cartId    = cart.getId();
            this.cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok( "deleted successfully" );
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body("not found cart with id " );
        }
    }

    @PutMapping("/Item")
    public ResponseEntity<Object> ProductDetail(HttpServletRequest request ,
                                                @RequestParam  Long productId
    ) {

        try {
            int userId = this.jwtService.UserId(request);
            Cart cart = this.cartService.getCartByUserId(userId);
            Long cartId    = cart.getId();
            CartItem cartItem = this.cartItemService.getCartItem(cartId, productId);
            return ResponseEntity.ok( cartItem );
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body("erreur cart with id " + e.getMessage() );
        }
    }






//    @PutMapping("/update/{cartId}")
//    public ResponseEntity<Object> UpdateItemQuantity(@PathVariable Long cartId ,
//                                                     @RequestParam Integer quantity ,
//                                                     @RequestParam  Long productId
//    ) {
//
//        try {
//            this.cartItemService.updateItemQuantity(cartId, productId, quantity);
//            return ResponseEntity.ok( "updateted successfully" );
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(NOT_FOUND).body("erreur cart with id " );
//        }
//    }








}
