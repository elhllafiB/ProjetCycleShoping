package com.example.register.AllSecurity.Controller;

import com.example.register.AllSecurity.Dto.UtilisateurDto;
import com.example.register.AllSecurity.Dto.AuthentificationDto;
import com.example.register.AllSecurity.Entity.Utilisateur;
import com.example.register.AllSecurity.Service.JwtService;
import com.example.register.AllSecurity.Service.UtilisateurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.register.AllSecurity.Repository.UtilisateurRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


import static org.springframework.http.HttpStatus.NOT_FOUND;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // Autorise Angular à faire des requêtes

public class UtilisateurController {



    // l injection de dependance est fait par construteur @AllArgsConstructor
    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/profile")
    public ResponseEntity<Object> profile(HttpServletRequest request) {
        try {
            int userId = this.jwtService.UserId(request);
            Utilisateur user = utilisateurRepository.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
            }

            // Retourne seulement nom, prénom et email
            UtilisateurDto UtilisateurDto = new UtilisateurDto(user.getNom(), user.getPrenom(), user.getEmail());

            return ResponseEntity.ok(UtilisateurDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable");
        }
    }


//    @GetMapping("/profile")
//    public ResponseEntity<Object> profile (HttpServletRequest request) {
//
//        try {
//            int userId = this.jwtService.UserId(request);
//            Utilisateur user = utilisateurRepository.findById(userId).orElse(null);
//
//            return ResponseEntity.ok(user);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(NOT_FOUND).body("not found cart with id " );
//        }
//
//
//    }


    @PostMapping("inscription")
    public String inscription(@RequestBody Utilisateur utilisateur) {
        this.utilisateurService.Register(utilisateur);
        return  "bien enregistrer";


    }

    @PostMapping("activation")
    // on recupere le code du body
    public String activation(@RequestBody Map<String , String > code) {

        this.utilisateurService.activation(code);
        return  "bien enregistrer";


    }



    // on return une chaine de char qui sera le token et la valeur du token
//    @PostMapping("login")
//    public Map<String , String > login(@RequestBody AuthentificationDto authentificationDto) {
//
//        //La méthode authenticate() permet de vérifier si les informations d'authentification fournies sont correctes.
//     final Authentication Authenticate =  authenticationManager.authenticate(
//                   new UsernamePasswordAuthenticationToken(authentificationDto.email(), authentificationDto.password())
//        );
//
//     if(Authenticate.isAuthenticated()) {
//        return  this.jwtService.generateToken(authentificationDto.email());
//     }
//
//
//
//     return null;
//    }



    @PostMapping("login")
    public Map<String, String> login(@RequestBody AuthentificationDto authentificationDto) {
        // Authentification de l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDto.email(), authentificationDto.password())
        );

        if (authentication.isAuthenticated()) {
            // Générer le token et récupérer le rôle
            Map<String, String> jwtMap = new HashMap<>(this.jwtService.generateToken(authentificationDto.email()));


            // Ajouter le rôle dans la réponse
            String token = jwtMap.get("bearer");
            String role = this.jwtService.getClaims(token, claims -> claims.get("role").toString());

            jwtMap.put("role", role);
            return jwtMap;
        }

        return null;
    }


//   @PostMapping("deconnecter")
//    public void deconnecter(HttpServletRequest request) {
//        this.jwtService.deconnecter( request);
//
//    }




//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
//        String token = authHeader.substring(7); // Retirer "Bearer "
//        jwtService.logout(token);
//        return ResponseEntity.ok("Déconnexion réussie");
//    }






}