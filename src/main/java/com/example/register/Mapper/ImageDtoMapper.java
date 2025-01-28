package com.example.register.Mapper;


import com.example.register.DTO.ImageDto2;
import com.example.register.Entity.Image;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageDtoMapper {


    public ImageDto2 toDto(Image image) {
        ImageDto2 imageDto = new ImageDto2();

        imageDto.setDownloadUrl(image.getDownloadUrl());
        return imageDto;

    }


    public List<ImageDto2> toDtolist(List<Image> images) {
        return images.stream().map(this::toDto).collect(Collectors.toList());
    }








}
