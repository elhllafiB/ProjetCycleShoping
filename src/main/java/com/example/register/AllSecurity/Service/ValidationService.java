package com.example.register.AllSecurity.Service;


import com.example.register.AllSecurity.Entity.Utilisateur;
import com.example.register.AllSecurity.Entity.Validation;
import com.example.register.AllSecurity.Entity.ValisationUpdatePassword;

import com.example.register.AllSecurity.Repository.ValidationRepository;
import com.example.register.AllSecurity.Repository.ValidationUpdateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

import static java.util.concurrent.TimeUnit.MINUTES;

@AllArgsConstructor
@Service
public class ValidationService {


    private ValidationRepository validationRepository;
    private NotificationService notificationService;
    private ValidationUpdateRepository validationUpdateRepository;



    public void ValidationdeUpdatPass(Utilisateur utilisateur) {
        ValisationUpdatePassword validationU = new ValisationUpdatePassword();
        validationU.setUtilisateur(utilisateur);
        Instant creation = Instant.now();
        validationU.setCreateTime(creation);


        // apres 10 min
        Instant expiration = creation.plus(10, MINUTES.toChronoUnit());
        validationU.setExpireTime(expiration);

        Random random = new Random();
        int randomInt = random.nextInt(10000);  // Nombre entre 0 et 9999
        String code = String.format("%04d", randomInt); // Formatage à 4 chiffres
        validationU.setCode(code);

        this.validationUpdateRepository.save(validationU);
        //lorsqu on enregistre une validation en envoyer directement un email qui contient le code
        this.notificationService.envoyerU(validationU);



    }
    public  void Register(Utilisateur utilisateur){

        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);
       Instant creation = Instant.now();
        validation.setCreateTime(creation);


        // apres 10 min
        Instant expiration = creation.plus(10, MINUTES.toChronoUnit());
        validation.setExpireTime(expiration);

        Random random = new Random();
        int randomInt = random.nextInt(10000);  // Nombre entre 0 et 9999
        String code = String.format("%04d", randomInt); // Formatage à 4 chiffres
        validation.setCode(code);

        this.validationRepository.save(validation);
        //lorsqu on enregistre une validation en envoyer directement un email qui contient le code
        this.notificationService.envoyer(validation);

    }

    public Validation lireEnFonctionDucode(String code){

        return this.validationRepository.findBycode(code).orElseThrow(()->new RuntimeException("votre code invalide"));
    }



    public ValisationUpdatePassword lireEnFonctionDucodeU(String code){

        return this.validationUpdateRepository.findBycode(code).orElseThrow(()->new RuntimeException("votre code invalide"));
    }



}



