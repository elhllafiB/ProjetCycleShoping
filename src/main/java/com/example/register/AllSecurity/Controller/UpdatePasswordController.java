package com.example.register.AllSecurity.Controller;


import com.example.register.AllSecurity.Service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor

public class UpdatePasswordController {



    @Autowired
    private UtilisateurService utilisateurService;



    @PostMapping("UpdatePassword")
    // on recupere le code du body
    public void  UpdatePassword(@RequestBody Map<String , String > code) {

        this.utilisateurService.UpdatePassword(code);



    }



    @PostMapping("newpassword")
    // on recupere le code du body
    public void  newpassword(@RequestBody Map<String , String > code) {

        this.utilisateurService.newpassword(code);



    }
}
