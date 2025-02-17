package com.example.register.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import java.sql.Blob;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Image {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;

    @JsonIgnore
    @Lob
    private Blob image;
    private String downloadUrl;


    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;


    public void setId(Long id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public Blob getImage() {
        return image;
    }

//    public Product getProduct() {
//        return product;
//    }
//
//    public String getDownloadUrl() {
//        return downloadUrl;
//    }
}