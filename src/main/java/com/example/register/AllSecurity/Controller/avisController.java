package com.example.register.AllSecurity.Controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor

@RestController
public class avisController {




    @GetMapping("hello")
    public String Hello(){
        return "hello";
    }
}
