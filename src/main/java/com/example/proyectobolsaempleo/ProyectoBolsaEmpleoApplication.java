package com.example.proyectobolsaempleo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class ProyectoBolsaEmpleoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoBolsaEmpleoApplication.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/empresa/Puestosrecienregistrados";
    }

}
