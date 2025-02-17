package com.example.register.AllSecurity.Service;


import com.example.register.AllSecurity.Entity.JwtT;
import com.example.register.AllSecurity.Entity.Utilisateur;

import com.example.register.AllSecurity.Repository.JwtTRepository;
import com.example.register.AllSecurity.Repository.UtilisateurRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Transactional
@AllArgsConstructor
@Service
public class JwtService {



    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);



    private final String Encryption = "608f36e92dc66d97d5933f0e6371493cb4fc05b1aa8f8de64014732472303a7c";
    @Autowired
    private RestClient.Builder builder;

    @Autowired
    private UtilisateurService UtilisateurService;



    @Autowired
    private JwtTRepository jwtTRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;




    //ca j ai configurer dans la 2 eme vedio

//    public Map<String , String > generateToken(String username ){
//
//
//      Utilisateur utilisateur = this.UtilisateurService.loadUserByUsername(username);
//
//
//      return this.generatejwt(utilisateur);
//
//    }






    public Map<String , String > generateToken(String username ){


      Utilisateur utilisateur = this.UtilisateurService.loadUserByUsername(username);

      final Map<String, String> jwtmap = this.generatejwt(utilisateur);


        //methode pour enregistrer token dans la DB
        saveUserToken(jwtmap, utilisateur);


        return jwtmap;


    }

    private void saveUserToken(Map<String, String> jwtmap, Utilisateur utilisateur) {
        JwtT jwtT = new JwtT();

        jwtT.setToken(jwtmap.get("bearer"));
        jwtT.setLoggeout(false);
        jwtT.setUtilisateur(utilisateur);
        jwtTRepository.save(jwtT);
    }


    private Map<String , String > generatejwt(Utilisateur utilisateur){

        final long  currentTime = System.currentTimeMillis();
        final long exprirationTime = currentTime + 60 * 60 * 1000;

        final Map<String, Object >  claims = Map.of(
                "role", utilisateur.getRole(),
                "email", utilisateur.getEmail(),
                "id", utilisateur.getId(),
                Claims.EXPIRATION , new Date(exprirationTime) ,
                Claims.SUBJECT , utilisateur.getEmail()
        );



        final String Bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(exprirationTime))
                .setSubject(utilisateur.getEmail())
                .setClaims(claims)
                .signWith(getkey(),SignatureAlgorithm.HS256)
                .compact();

        return Map.of("bearer",Bearer);

    }


    private Key getkey(){
        final byte[] keyBytes = Decoders.BASE64.decode(Encryption);
        return Keys.hmacShaKeyFor(keyBytes);
    }




    public <T> T getClaims(String token, Function<Claims,T> function ){
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }


    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getkey()) // La clé utilisée pour signer le JWT
                .build()
                .parseClaimsJws(token) // Remplace parsePlaintextJwt par parseClaimsJws
                .getBody();
    }


    public String ExtractUsername(String token) {
        return this.getClaims(token , Claims::getSubject);
    }

    public boolean IsTokenExpired(String token) {
        Date expirationDate = this.getClaims(token , Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    public boolean IsTokenlogout(String token) {

      boolean isValide = this.jwtTRepository.findByToken(token).map(t->!t.isLoggeout()).orElse(false);

      if(isValide== false){
          return false;
      }
      return true;
    }





    private void revokeAllUSertoken(Utilisateur utilisateur){
        List<JwtT> valideUserToken = jwtTRepository.findAllTokenByUtilisateur(utilisateur.getId());

        if(!valideUserToken.isEmpty()){
            valideUserToken.forEach(T -> T.setLoggeout(true));
        }

        jwtTRepository.saveAll(valideUserToken);

    }


    public int UserId(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid or missing Authorization header");
        }
        String token = authorizationHeader.substring(7);
        int id  = this.getClaims(token, claims -> Integer.parseInt(claims.get("id").toString()));
        return id;
    }


    public void logout(HttpServletRequest request) {
        // Extraire le token de l'en-tête Authorization


        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid or missing Authorization header");
        }
        // Supprimer le préfixe "Bearer " pour obtenir le token brut
        String token = authorizationHeader.substring(7);
        // Extraire l'email depuis le token
        String email = this.getClaims(token, Claims::getSubject);
        // Trouver l'utilisateur par email
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));



        // Trouver les tokens actifs associés à l'utilisateur
        List<JwtT> activeTokens = jwtTRepository.findAllTokenByUtilisateur(utilisateur.getId());

        if (activeTokens.isEmpty()) {
            throw new RuntimeException("No active tokens found for the user.");
        }

        // Marquer tous les tokens comme déconnectés

        if (!activeTokens.isEmpty()) {
            activeTokens.forEach(tokenEntity -> tokenEntity.setLoggeout(true));

        }


        jwtTRepository.saveAll(activeTokens);
    }

}
