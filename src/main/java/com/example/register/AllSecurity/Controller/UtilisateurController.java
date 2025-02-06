package com.example.register.AllSecurity.Controller;

import java.util.HashMap;
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

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
//@RequestMapping("/api")

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






