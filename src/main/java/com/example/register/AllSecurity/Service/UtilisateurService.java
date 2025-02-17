package com.example.register.AllSecurity.Service;


import com.example.register.AllSecurity.Entity.Role;
import com.example.register.AllSecurity.Entity.TypeRole;
import com.example.register.AllSecurity.Entity.Utilisateur;
import com.example.register.AllSecurity.Entity.Validation;
import com.example.register.AllSecurity.Entity.ValisationUpdatePassword;
import com.example.register.AllSecurity.Repository.UtilisateurRepository;
import com.example.register.Entity.Cart;
import com.example.register.Repository.CartRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor

public class UtilisateurService  implements UserDetailsService {




    //!!!!??? attention meme s il y a  @AllArgsConstructor il faut ajouter l annotation @Autowired pour s effetue la dependence est ne s affiche pas null pointer


    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private CartRepository cartRepository;









    public void Register(Utilisateur utilisateur) {


        if (utilisateur.getEmail() == null || utilisateur.getPassword() == null) {
            throw new RuntimeException("Email ou mot de passe est null");
        }

        if(!utilisateur.getEmail().contains("@")){
            throw new RuntimeException(" votre email invalide");
        }
        if(!utilisateur.getEmail().contains(".")){
            throw new RuntimeException(" votre email invalide");
        }

        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByEmail(utilisateur.getEmail());

        if(utilisateurOptional.isPresent()){
            throw new RuntimeException(" votre email exist");
        }

        //cette ligne pour crypter le mot de passe ( 1234 => bla bla bla)
        String mdpcrypte =  this.passwordEncoder.encode(utilisateur.getPassword());
        //modifier le mot de passe de l utilisateur par le mot de passe crypter
        utilisateur.setMdp(mdpcrypte);
        // puis enregistre l utilisateur dans la base de donne avec un mot de passe crypter

        Role userRole = new Role();
        userRole.setLibelle(TypeRole.utilisateur);
        utilisateur.setRole(userRole);
        utilisateur=   utilisateurRepository.save(utilisateur);
        //lorsqu on enregistre un utilisateur on appel la methode de registre dont on genere un mot de passe de validation
        this.validationService.Register(utilisateur);



        Cart cart = new Cart();
        cart.setUser(utilisateur);
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);




    }






    public void activation(Map<String, String> code) {
        Validation validation = this.validationService.lireEnFonctionDucode(code.get("code"));
        // si le code est expire => affiche message d erreur
        if(Instant.now().isAfter(validation.getExpireTime())) {
            throw new RuntimeException(" votre code a expire");
        }
        // on cherche l utilisateur par son code
        Utilisateur utilisateuractivate = this.utilisateurRepository.findById(validation.getUtilisateur().getId()).orElseThrow(()->new RuntimeException(" utilisateur inconnue "));
        //si on trouve l utilisateur    private boolean actif = false; =>   private boolean actif = true;
        utilisateuractivate.setActif(true);

        // enregistrer le changement dans la table de l utilisateur
        this.utilisateurRepository.save(utilisateuractivate);
    }








    // cette methode on a redefinie ( de l interface UserDetailsService )
    // le role de cette methode est de chercher un utilisateur de la base de donne en fonction de leur mot de passe
    @Override
    public Utilisateur loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.utilisateurRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("aucun utilisateur avec cette email"));
        // s il y a user on le return sinon on leve un eexception
    }


    //contient seulement l email pour verifier le mot de passe
    public void UpdatePassword(Map<String, String> parametre) {

        Utilisateur utilisateur = this.loadUserByUsername(parametre.get("email"));
        this.validationService.ValidationdeUpdatPass(utilisateur);
    }


    // cette map contient le code , new password
    public void newpassword(Map<String, String> parametres) {
        Utilisateur utilisateur = this.loadUserByUsername(parametres.get("email"));
        final ValisationUpdatePassword validationU = validationService.lireEnFonctionDucodeU(parametres.get("code"));
        if(validationU.getUtilisateur().getEmail().equals(utilisateur.getEmail())){
            //ca le mot de passe saisie par l utilisateur
            String newmdp = parametres.get("password");
            // il faut crypte le mot de passe
            String mdpcrypte =  this.passwordEncoder.encode(newmdp);
            utilisateur.setMdp(mdpcrypte);

            this.utilisateurRepository.save(utilisateur);

        }


    }
}