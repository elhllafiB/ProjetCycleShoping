package com.example.register.Mapper;

import com.example.register.DTO.ImageDto2;
import com.example.register.DTO.ProductDTO;
import com.example.register.DTO.ProductDto2;
import com.example.register.Entity.Image;
import com.example.register.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class ProductMapper2 {

    @Autowired
    private ImageDtoMapper imageDto;






    public ProductDto2 toDto(Product product) {

        ProductDto2 productDto2 = new ProductDto2();
        productDto2.setId(product.getId());
        productDto2.setName(product.getName());
        productDto2.setPrice(product.getPrice());
        productDto2.setDescription(product.getDescription());
        productDto2.setBrand(product.getBrand());



        // Mapper la liste des images
        List<ImageDto2> imageDtoList = imageDto.toDtolist(product.getImages());

        //productDto2.setImages(imageDtoList); // Assurez-vous que ProductDto2 possède une liste de `ImageDto2`.
        return productDto2;

    }


    public List<ProductDto2> toDtoList(List<Product> products) {
        return products.stream().map(productDto2 -> toDto(productDto2)).collect(Collectors.toList());
    }






}
