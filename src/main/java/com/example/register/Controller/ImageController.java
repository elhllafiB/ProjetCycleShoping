package com.example.register.Controller;


import com.example.register.DTO.ImageDto;
import com.example.register.Entity.Image;
import com.example.register.Service.ImageService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@RestController
public class ImageController {


    @Autowired
    private ImageService imageService;



    @GetMapping("/Image/upload")
    public ResponseEntity<List<ImageDto>> uploadImages(@RequestParam("productId") Long productId, @RequestParam("files") List<MultipartFile> files) {

        try {
            // Appeler la méthode saveImages du service pour enregistrer les images
            List<ImageDto> savedImages = imageService.saveImages(productId, files);
            return ResponseEntity.ok(savedImages); // Retourner une réponse avec la liste des DTOs
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null); // Retourner un statut d'erreur en cas de problème
        }
    }



    @GetMapping("/api/v1/images/image/download/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long id) {
        try {
            // Appeler la méthode pour récupérer l'image par son ID
            Image image = imageService.getImageById(id); // Récupérer l'image avec l'ID

            if (image == null) {
                // Si l'image n'est pas trouvée, retourner une erreur 404
                return ResponseEntity.notFound().build();
            }

            // Convertir l'image en ByteArrayResource pour pouvoir la renvoyer comme fichier
            ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

            // Retourner la réponse avec l'image et le type MIME
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.valueOf(image.getFileType())) // Définir le type MIME de l'image
                    .header("Content-Disposition", "attachment; filename=" + image.getFileName()) // Indiquer qu'il s'agit d'un fichier à télécharger
                    .body(resource); // Le contenu de l'image dans la réponse
        } catch (SQLException e) {
            // Si une exception SQL se produit, retourner une erreur 500
            return ResponseEntity.status(500).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    @DeleteMapping("/api/v1/images/image/delete/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        try {
            // Appeler la méthode pour supprimer l'image par son ID
            imageService.deleteImageById(id); // Appel du service pour supprimer l'image

            // Retourner une réponse avec un message de succès
            return ResponseEntity.ok("Image deleted successfully"); // Réponse 200 OK avec message
        } catch (Exception e) {
            // En cas d'erreur, retourner une réponse 404 ou 500 selon le cas
            return ResponseEntity.status(404).body("Image not found"); // Réponse 404 si l'image n'est pas trouvée
        }
    }
















}


