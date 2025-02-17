package com.example.register.Controller;

import com.example.register.AllSecurity.Entity.Utilisateur;
import com.example.register.AllSecurity.Repository.UtilisateurRepository;
import com.example.register.AllSecurity.Service.JwtService;
import com.example.register.DTO.ProductDTO;
import com.example.register.Entity.Product;
import com.example.register.Mapper.ProductMapper;
import com.example.register.Request.AddProductRequest;
import com.example.register.Service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@AllArgsConstructor
@NoArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private ProductMapper productMapper;



    @GetMapping("/product/getbyid/{id}")
    public Product getProductById(@PathVariable Long id){


        try {
            return  productService.getProductById2(id)   ;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




   @GetMapping("/product/getbyuser")
    public List<ProductDTO>  getProductByUser(HttpServletRequest request){

        try{
            int userId = this.jwtService.UserId(request);
            return productService.getProductByUserId(userId);

        }catch (Exception e){
            throw new RuntimeException(e);

        }
   }


   @DeleteMapping("/product/delet/{id}")
    public void deleteProduct(@PathVariable Long id){

       try {
           productService.deleteProduct(id);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }

//
   @PostMapping("/product/add")
    public  ProductDTO addProduct(@RequestBody Product product , HttpServletRequest request){


       try {
           // Récupérer l'utilisateur connecté
          int  currentUser = this.jwtService.UserId(request);
          Utilisateur utilisateur = utilisateurRepository.findById(currentUser).get();
           product.setUser(utilisateur);

           ProductDTO theProduct = productService.addProduct(product);

           return theProduct;
       } catch (Exception e) {
           throw new RuntimeException(e);
       }

   }






    @PutMapping("/product/update/{productId}")
    public ProductDTO updateProduct(@PathVariable Long productId, @RequestBody Product request) {
        try {
            // Appeler la méthode updateProduct du service
            Product updatedProduct = productService.updateProduct(request, productId);

           ProductDTO P = productMapper.toDTO(updatedProduct);
          return P;
        } catch (Exception e) {
            // En cas d'erreur (par exemple, produit non trouvé)
          throw new RuntimeException("erreur lors de l'update product");
        }
    }



    @GetMapping("/product/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        try {
            List<ProductDTO> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/product/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category) {
        try {
            List<ProductDTO> products = productService.getProductsByCategory(category);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




}
