package com.example.register.Controller;


import com.example.register.Service.OrderService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@NoArgsConstructor
@RestController
public class OrderController {




    @Autowired
    private OrderService orderService;


    @GetMapping("/myOrder/{userId}")
    public ResponseEntity<Object> createOrder(@PathVariable int userId) {

        try {

            orderService.placeOrder(userId);
            return ResponseEntity.ok("Item Order Success!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }







}
