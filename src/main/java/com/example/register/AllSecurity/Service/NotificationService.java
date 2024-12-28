package com.example.register.AllSecurity.Service;



import com.example.register.AllSecurity.Entity.ValisationUpdatePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.register.AllSecurity.Entity.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class NotificationService {



    @Autowired
     JavaMailSender javaMailSender;

    public void envoyer (Validation validation ){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bassma.el@gmail.com");
        message.setTo(validation.getUtilisateur().getEmail() );
        message.setSubject("Votre code d activation");

        String texte = String.format(
                "Bonjour %s, <br /> Votre code d'action est %s; A bientôt",
                validation.getUtilisateur().getNom(),
                validation. getCode()
        );
        message.setText(texte);
        javaMailSender.send(message);
    }

    public void envoyerU(ValisationUpdatePassword validationU) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bassma.el@gmail.com");
        message.setTo(validationU.getUtilisateur().getEmail() );
        message.setSubject("Votre code d activation");

        String texte = String.format(
                "Bonjour %s, <br /> Votre code d'action est %s; A bientôt",
                validationU.getUtilisateur().getNom(),
                validationU. getCode()
        );
        message.setText(texte);
        javaMailSender.send(message);
    }
}
