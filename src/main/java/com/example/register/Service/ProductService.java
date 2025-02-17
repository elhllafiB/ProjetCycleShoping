package com.example.register.Service;

import com.example.register.AllSecurity.Entity.Utilisateur;
import com.example.register.DTO.ProductDTO;

import com.example.register.Entity.Product;
import com.example.register.Mapper.ProductMapper;

import com.example.register.Repository.ProductRepository;
import com.example.register.Request.AddProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;


    public Product getProductById(Long productId) {
        return productRepository.findById(productId).get();
    }


    public Product getProductById2(Long productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Produit non trouvé avec l'ID : " + productId));
    }


    public List<Product> getProductByUserName(int id) throws Exception {

        List<Product> Prod = productRepository.findByUserId(id);

        if (Prod.isEmpty()) {
            throw new Exception("product not found");
        }

        return Prod;

    }


    public List<ProductDTO> getProductByUserId(int userId) throws Exception {
        List<Product> products = productRepository.findByUserId(userId);

        if (products.isEmpty()) {
            throw new Exception("No products found for the user");
        }

        // Utiliser le mapper pour convertir la liste
        return productMapper.toDTOList(products);
    }


    public void deleteProduct(Long productId) throws Exception {

        if (!productRepository.existsById(productId)) {
            throw new Exception("product not found");
        }
        productRepository.deleteById(productId);
    }

    public ProductDTO addProduct(Product product)  {

        Product p = productRepository.save(product);
        return productMapper.toDTO(p);

    }








    public Product updateProduct(Product request, Long productId) throws Exception {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct,request))
                .map(productRepository :: save)
                .orElseThrow(()-> new Exception("Product not found!"));
    }


    private Product updateExistingProduct(Product existingProduct,  Product request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());


        return  existingProduct;

    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toDTO).collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }


}