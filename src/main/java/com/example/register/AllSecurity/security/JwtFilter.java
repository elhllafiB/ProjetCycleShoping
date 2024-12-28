package com.example.register.AllSecurity.security;


import com.example.register.AllSecurity.Service.JwtService;
import com.example.register.AllSecurity.Service.UtilisateurService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Service
public class JwtFilter extends OncePerRequestFilter {


    @Autowired
private UtilisateurService utilisateurService;
    @Autowired
private JwtService jwtService;


    public JwtFilter(JwtService jwtService, UtilisateurService utilisateurService) {
        this.jwtService = jwtService;
        this.utilisateurService = utilisateurService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


      String token ;
      // pour veridfier si le token existe dans la base de donne

      String username = null;
      boolean IsTokenExpired = true ;
        boolean IsTokenlogout = false ;

      final String authorization = request.getHeader("Authorization");
     //  la méthode substring(7) extrait une sous-chaîne (ou une portion de chaîne de caractères) à partir de l'index 7 d'une chaîne de caractères.
     // on recupere le token , on prend en consideration l espace de 7 caractere


      if(authorization!=null && authorization.startsWith("Bearer ")) {

          token = authorization.substring(7);
          //ici je verifier si le token dans la base de donne sinon on leve la exception
         // jwtinDB=  this.jwtService.tokenByValeur(token);
          IsTokenlogout = jwtService.IsTokenlogout(token);

          IsTokenExpired =  jwtService.IsTokenExpired(token);
          //ca le token qu on a recuperer du header
          username = jwtService.ExtractUsername(token);
      }

      if( !IsTokenExpired && username != null && SecurityContextHolder.getContext().getAuthentication() == null && IsTokenlogout == true) {

          UserDetails userDetails = utilisateurService.loadUserByUsername(username);
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }


      filterChain.doFilter(request, response);
    }
}
