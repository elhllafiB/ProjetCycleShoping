package com.example.register.Service;


import com.example.register.Entity.Cart;
import com.example.register.Entity.CartItem;
import com.example.register.Entity.Product;
import com.example.register.Repository.CartItemRepository;
import com.example.register.Repository.CartRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CartItemService {








    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;


//    @Transactional
//    public void addItemToCart(Long cartId, Long productId, int quantity) {
//        //1. Get the cart
//        //2. Get the product
//        //3. Check if the product already in the cart
//        //4. If Yes, then increase the quantity with the requested quantity
//        //5. If No, then initiate a new CartItem entry.
//        Cart cart;
//        cart = cartService.getCart(cartId); // Récupérer la carte existante
//        System.out.println("Cart ID: " + cart.getId());
//
////        if (cartId == null || !cartRepository.existsById(cartId)) {
////            System.out.println("Cart does not exist. Creating a new one.");
////            cart = new Cart();
////            cart = cartRepository.save(cart); // Sauvegarder la nouvelle carte pour obtenir un ID
////        }
//
//        if(cart == null) {
//            System.out.println("Cart does not exist. Creating a new one.");
//            cart = new Cart();
//            cart.setTotalAmount((BigDecimal.valueOf(0)));
//          // Sauvegarder la nouvelle carte pour obtenir un ID
//        }
//
//        System.out.println(cart.getId());
//        Product product = productService.getProductById(productId);
//        System.out.println(product.getId());
//        CartItem cartItem = cart.getItems()
//                .stream()
//                .filter(item -> item.getProduct().getId().equals(productId))
//                .findFirst().orElse(new CartItem());
//
//        System.out.println(cartItem.getId());
//        if (cartItem.getId() == null) {
//            System.out.println("entre dans if");
//            cartItem.setCart(cart);
//            cartItem.setProduct(product);
//            cartItem.setQuantity(quantity);
//            cartItem.setUnitPrice(product.getPrice());
//        }
//        else {
//            System.out.println("entre dans else");
//            cartItem.setQuantity(cartItem.getQuantity() + quantity);
//        }
//        System.out.println("sortie du else");
//        cartItem.setTotalPrice();
//        cart.addItem(cartItem);
//        cartItemRepository.save(cartItem);
//        cartRepository.save(cart);
//    }



    @Transactional
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //1. Get the cart
        //2. Get the product
        //3. Check if the product already in the cart
        //4. If Yes, then increase the quantity with the requested quantity
        //5. If No, then initiate a new CartItem entry.
//        Cart cart = cartService.getCart(cartId);
//        System.out.println(cart.getId());

        Cart cart;

        // Vérifiez si le panier existe
        boolean exists = cartRepository.existsById(cartId);

        if (exists) {
            // Si le panier existe, le récupérer
            cart = cartService.getCart(cartId);
            System.out.println("Panier trouvé: " + cart.getId());
        } else {
            // Si le panier n'existe pas, en créer un nouveau
            System.out.println("Aucun panier trouvé avec l'ID: " + cartId);
            cart = new Cart(); // Création d'un nouveau panier
            cart.setTotalAmount(BigDecimal.ZERO);
            cartRepository.save(cart);
        }

        System.out.println("ID du panier en cours: " + cart.getId());

        System.out.println(cart.getId());

        Product product = productService.getProductById(productId);
        System.out.println(product.getId());
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());

        System.out.println(cartItem.getId());
        if (cartItem.getId() == null) {
            System.out.println("entre dans if");
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else {
            System.out.println("entre dans else");
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        System.out.println("sortie du else");
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }















    public void removeItemFromCart(Long cartId, Long productId) {

        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems().stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()->new RuntimeException("Item Not Found"));

        cart.removeItem(cartItem);
        cartRepository.save(cart);
    }




    //pas encore tester cette methode ??!!!!!
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {

        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);


        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(product.getPrice());
                    item.setTotalPrice();
                    cartItemRepository.save(item);
                });
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount );
        cartRepository.save(cart);




    }


    // ca juster pour ne pas repeter cette partie duc code dans les autre methode , on fait juste l appele
    // pas encore tester !!????
    public CartItem getCartItem(Long cartId , Long productId) {


        Cart cart = cartService.getCart(cartId);
        CartItem cartItem= cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()->new RuntimeException("Item Not Found"));
        return cartItem;
    }









}
