package com.example.register.AllSecurity.Controller;


import com.example.register.AllSecurity.Service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class logoutController {



    @Autowired
    private JwtService jwtService;

    public logoutController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @PostMapping("/deconnection")
    public void  deconn(HttpServletRequest request) {


        this.jwtService.logout(request);

    }
}
