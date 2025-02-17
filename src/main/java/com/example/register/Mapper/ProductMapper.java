package com.example.register.Mapper;

import com.example.register.DTO.ProductDTO;
import com.example.register.Entity.Product;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;



@Component
public class ProductMapper {



    public ProductDTO toDTO(Product product) {


        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        dto.setInventory(product.getInventory());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());

        dto.setImages(product.getImages());
        return dto;

    }


    public List<ProductDTO> toDTOList(List<Product> products) {

        return products.stream().map(productDTO -> toDTO(productDTO)).collect(Collectors.toList());

    }


    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        product.setInventory(dto.getInventory());
        product.setDescription(dto.getDescription());

        product.setImages(dto.getImages());
        return product;
    }



}
