package com.example.register.DTO;


import com.example.register.Entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;




@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {



    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;

    private List<Image> images;
}
