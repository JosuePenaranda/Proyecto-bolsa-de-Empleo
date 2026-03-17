package com.example.proyectobolsaempleo.presentation.empresa;

import com.example.proyectobolsaempleo.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller("empresas")
public class Controller {
    @Autowired
    private Service service;

    // Dashboard
    @GetMapping("/presentation/oferentes/empresa/dashboard")
    public String show(Model model) {
        return "presentation/oferentes/empresa/Dashboard";
    }

}
