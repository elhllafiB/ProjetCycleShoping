package com.example.register.Service;


import com.example.register.Entity.Cart;
import com.example.register.Entity.OrderItem;
import com.example.register.Entity.Product;
import com.example.register.Entity.Client_Order;
import com.example.register.Repository.OrderRepository;
import com.example.register.Repository.ProductRepository;
import com.example.register.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderService {


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderRepository orderRepository;




    @Transactional

    public Client_Order placeOrder(int userId) {
        Cart cart   = cartService.getCartByUserId(userId);
        Client_Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Client_Order savedOrder = orderRepository.save(order);
      //  cartService.clearCart(cart.getId());
        return savedOrder;
    }




    private Client_Order createOrder(Cart cart) {
        Client_Order order = new Client_Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return  order;
    }


    private List<OrderItem> createOrderItems(Client_Order order, Cart cart) {
        return  cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return  new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();

    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return  orderItemList
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }










}
