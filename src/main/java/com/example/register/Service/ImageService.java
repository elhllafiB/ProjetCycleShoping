package com.example.register.Service;

import com.example.register.DTO.ImageDto;
import com.example.register.Entity.Image;
import com.example.register.Entity.Product;
import com.example.register.Repository.ImageRepository;
import com.example.register.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
@AllArgsConstructor
@NoArgsConstructor
public class ImageService {



    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ProductService productService;


    public Image getImageById(Long id) throws Exception{

        return imageRepository.findById(id).orElseThrow(()->new Exception("ressource not found "));
    }




    public void deleteImageById(Long id) throws Exception{

        Image image = imageRepository.findById(id).orElseThrow(()->new Exception("ressource not found"));
        imageRepository.delete(image);


    }

    public void updateImage(MultipartFile file, Long id) throws Exception {
        // Récupérer l'objet Image à partir de l'ID fourni
        Image image = getImageById(id);

        try {
            // Mettre à jour le nom du fichier avec le nom du fichier d'origine
            image.setFileName(file.getOriginalFilename());

            // Mettre à jour le type du fichier (en utilisant le nom de fichier, ce n'est pas idéal pour obtenir le type réel)
            image.setFileType(file.getOriginalFilename());

            // Convertir le contenu du fichier en tableau d'octets (Bytes) et stocker en tant que SerialBlob
            image.setImage(new SerialBlob(file.getBytes()));

            // Sauvegarder l'objet Image mis à jour dans la base de données
            imageRepository.save(image);

        } catch (IOException | SQLException e) {
            // Si une erreur survient pendant l'accès aux données du fichier ou lors de l'enregistrement du Blob, l'exception est attrapée et relancée
            throw new Exception(e.getMessage());
        }
    }






    public List<ImageDto> saveImages(Long productId, List<MultipartFile> files) {
        // 1. Récupérer le produit en utilisant son ID
        Product product = productService.getProductById(productId);

        // 2. Créer une liste pour stocker les informations des images enregistrées (sous forme de DTO)
        List<ImageDto> savedImageDto = new ArrayList<>();

        // 3. Itérer sur chaque fichier (image) envoyé dans la requête
        for (MultipartFile file : files) {
            try {
                // 4. Créer une nouvelle instance de l'entité Image
                Image image = new Image();

                // 5. Définir le nom du fichier téléchargé dans l'entité Image
                image.setFileName(file.getOriginalFilename());

                // 6. Définir le type du fichier (MIME type) dans l'entité Image
                image.setFileType(file.getContentType());

                // 7. Convertir le contenu du fichier en un tableau d'octets et le stocker dans un SerialBlob
                image.setImage(new SerialBlob(file.getBytes()));

                // 8. Associer l'image au produit en utilisant l'objet Product récupéré précédemment
                image.setProduct(product);

                // 9. Construire l'URL de téléchargement pour l'image
                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId(); // Il manque l'ID de l'image à ce moment-là, donc l'URL sera incorrect ici.
                image.setDownloadUrl(downloadUrl);

                // 10. Enregistrer l'image dans la base de données via le repository
                Image savedImage = imageRepository.save(image);

                // 11. Une fois l'image sauvegardée et obtenue avec un ID généré, reconstruire l'URL de téléchargement
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());

                // 12. Sauvegarder l'image mise à jour (avec le bon ID dans l'URL) dans la base de données
                imageRepository.save(savedImage);

                // 13. Créer un DTO (Data Transfer Object) pour l'image enregistrée
                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());            // L'ID de l'image
                imageDto.setFileName(savedImage.getFileName()); // Le nom du fichier
                imageDto.setDownloadUrl(savedImage.getDownloadUrl()); // L'URL de téléchargement

                // 14. Ajouter le DTO à la liste des images sauvegardées
                savedImageDto.add(imageDto);

            } catch (IOException | SQLException e) {
                // 15. Si une exception se produit lors de la gestion du fichier ou du BLOB, lancer une exception RuntimeException avec le message d'erreur
                throw new RuntimeException(e.getMessage());
            }
        }

        // 16. Retourner la liste des ImageDto contenant les informations sur les images enregistrées
        return savedImageDto;
    }










}