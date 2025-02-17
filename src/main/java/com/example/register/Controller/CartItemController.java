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
import java.util.Map;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import java.util.HashMap;  // Pour utiliser HashMap
import org.springframework.http.HttpStatus;  // Pour utiliser HttpStatus
import java.util.Optional;


@AllArgsConstructor
@NoArgsConstructor
@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = {"Authorization", "Content-Type"},
        //methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE},
        allowCredentials = "true"
)
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
    public ResponseEntity<Object> getCart(HttpServletRequest request ,@RequestParam Integer quantity ,@RequestParam  Long productId) {
        try {
            int userId = this.jwtService.UserId(request);
            Cart cart = this.cartService.getCartByUserId(userId);
            Long cartId    = cart.getId();
            this.cartItemService.addItemToCart(cartId, productId, quantity);
            //return ResponseEntity.ok().body(Map.of("message", "added successfully"));
            cart = this.cartService.getCartByUserId(userId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body("not found cart with id " );
        }
    }
    @GetMapping("/item/update/{productId}")
    public ResponseEntity<Object> updateItem(HttpServletRequest request,
                                             @PathVariable Long productId,
                                             @RequestParam Integer quantity) {

        try {
            int userId = this.jwtService.UserId(request);
            Cart cart = this.cartService.getCartByUserId(userId);
            Long cartId = cart.getId();
            this.cartItemService.addItemToCart(cartId, productId, quantity);
            cart = this.cartService.getCartByUserId(userId);
            return ResponseEntity.ok(cart);
            //return ResponseEntity.ok("updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body("not found cart with id");
        }
    }

//    @GetMapping("/item/update/{productId}")
//    public ResponseEntity<Object> updateItem(HttpServletRequest request,
//                                             @PathVariable Long productId,
//                                             @RequestParam Integer quantity) {
//        try {
//            int userId = this.jwtService.UserId(request);
//            Cart cart = this.cartService.getCartByUserId(userId);
//            Long cartId = cart.getId();
//
//            // Récupérer l'élément du panier existant
//            Optional<CartItem> existingCartItem = this.cartItemService.findByCartIdAndProductId(cartId, productId);
//
//            if (existingCartItem.isPresent()) {
//                // Récupérer l'élément de panier
//                CartItem cartItem = existingCartItem.get();
//
//                // Mettre à jour la quantité en fonction de la quantité actuelle
//                int newQuantity = cartItem.getQuantity() + quantity; // Incrémenter ou décrémenter la quantité
//
//                // Vérifier que la quantité ne devient pas négative
//                if (newQuantity < 0) {
//                    return ResponseEntity.badRequest().body("La quantité ne peut pas être inférieure à zéro.");
//                }
//
//                // Mettre à jour la quantité
//                cartItem.setQuantity(newQuantity);
//                this.cartItemService.save(cartItem); // Sauvegarder l'élément mis à jour
//
//                return ResponseEntity.ok("Produit mis à jour avec succès");
//            } else {
//                return ResponseEntity.status(NOT_FOUND).body("Produit non trouvé dans le panier");
//            }
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(NOT_FOUND).body("Erreur lors de la mise à jour du panier");
//        }
//    }



//    @DeleteMapping("/item/delete")
//    public ResponseEntity<Object> removeItemFromCart(HttpServletRequest request,
//                                                     @RequestParam Long cartItemId) {
//        try {
//            int userId = this.jwtService.UserId(request);
//            System.out.println("User ID: " + userId); // Debug
//
//            Cart cart = this.cartService.getCartByUserId(userId);
//            if (cart == null) {
//                return ResponseEntity.status(NOT_FOUND).body("Cart not found for user ID: " + userId);
//            }
//
//            Long cartId = cart.getId();
//            System.out.println("Cart ID: " + cartId); // Debug
//
//            // Vérifier si l’élément existe dans le panier
//            CartItem cartItem = this.cartItemService.getCartItemById(cartItemId);
//            System.out.println("CartItem found: " + cartItem);
//
//            if (cartItem == null) {
//                return ResponseEntity.status(NOT_FOUND).body("Item not found in cart");
//            }
//
//            this.cartItemService.removeItemFromCart(cartId, cartItemId);
//            return ResponseEntity.ok("Deleted successfully");
//
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(NOT_FOUND).body("Error: " + e.getMessage());
//        }
//    }



    @DeleteMapping("/Item/delete/{productId}")
    public ResponseEntity<Object> supprimerItemfromCart(HttpServletRequest request ,
                                                        @PathVariable Long productId
    ) {

        try {
            int userId = this.jwtService.UserId(request);
            Cart cart = this.cartService.getCartByUserId(userId);
            Long cartId    = cart.getId();
            this.cartItemService.removeItemFromCart(cartId, productId);
            //return ResponseEntity.ok( "deleted successfully" );
            cart = this.cartService.getCartByUserId(userId);
            return ResponseEntity.ok(cart);
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
            System.out.println("Requête reçue : productId = " + productId + ", userId = " + userId);
            return ResponseEntity.ok( cartItem );

        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body("erreur cart with id " + e.getMessage() );
        }
    }




    @PutMapping("/item/update")
    public ResponseEntity<Object> updateItemQuantity(
            HttpServletRequest request,
            @RequestBody Map<String, Object> payload) {

        try {
            int userId = this.jwtService.UserId(request);
            Cart cart = this.cartService.getCartByUserId(userId);
            Long cartId = cart.getId();

            Long productId = Long.valueOf(payload.get("productId").toString());
            int quantity = Integer.parseInt(payload.get("quantity").toString());
            this.cartItemService.updateItemQuantity(cartId, productId, quantity);
            System.out.println("Requête reçue : productId = " + productId + ", userId = " + userId);

            return ResponseEntity.ok("Quantité mise à jour avec succès !");
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body("Erreur lors de la mise à jour : " + e.getMessage());
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
