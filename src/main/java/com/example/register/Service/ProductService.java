package com.example.register.Service;

import com.example.register.Entity.Product;
import com.example.register.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).get();
    }
}
