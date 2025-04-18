package com.example.register.AllSecurity.Controller;


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

import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor


public class UtilisateurController {



    // l injection de dependance est fait par construteur @AllArgsConstructor
    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private AuthenticationManager authenticationManager;

   @Autowired
   private JwtService jwtService;




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
    @PostMapping("login")
    public Map<String , String > login(@RequestBody AuthentificationDto authentificationDto) {

        //La méthode authenticate() permet de vérifier si les informations d'authentification fournies sont correctes.
     final Authentication Authenticate =  authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(authentificationDto.email(), authentificationDto.password())
        );

     if(Authenticate.isAuthenticated()) {
        return  this.jwtService.generateToken(authentificationDto.email());
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






