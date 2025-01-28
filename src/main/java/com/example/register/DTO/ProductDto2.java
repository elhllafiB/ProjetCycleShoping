package com.example.register.DTO;

import com.example.register.Entity.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto2 {



    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;

    private List<ImageDto2> images;





}
