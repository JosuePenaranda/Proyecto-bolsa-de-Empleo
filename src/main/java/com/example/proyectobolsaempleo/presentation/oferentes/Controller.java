package com.example.proyectobolsaempleo.presentation.oferentes;

import com.example.proyectobolsaempleo.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller("oferentes")
public class Controller {
    @Autowired
    private Service service;

    // Dashboard
    @GetMapping("/presentation/oferentes/dashboard")
    public String show(Model model) {
        return "presentation/oferentes/Dashboard";
    }

    // Mis Habilidades
    @GetMapping("/presentation/oferentes/habilidades")
    public String habilidades(Model model) {
        return "presentation/oferentes/MisHabilidades";
    }

    // Mi CV (cuando tengas la página lista)
    @GetMapping("/presentation/oferentes/cv")
    public String cv(Model model) {
        return "presentation/oferentes/MiCV";
    }

}
