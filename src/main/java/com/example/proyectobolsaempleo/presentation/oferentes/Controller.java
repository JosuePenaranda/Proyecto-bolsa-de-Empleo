package com.example.proyectobolsaempleo.presentation.oferentes;

import com.example.proyectobolsaempleo.logic.ServiceLogin;
import com.example.proyectobolsaempleo.logic.ServiceOferente;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller("oferentes")
public class Controller {
    @Autowired
    private ServiceOferente serviceOferente;

    // Dashboard
    @GetMapping("/presentation/oferentes/dashboard")
    public String show(Model model, HttpSession session) {
        model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
        return "presentation/oferentes/Dashboard";
    }

    // Mis Habilidades
    @GetMapping("/presentation/oferentes/habilidades")
    public String habilidades(Model model, HttpSession session) {
        model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
        return "presentation/oferentes/MisHabilidades";
    }

    // Mi CV (cuando tengas la página lista)
    @GetMapping("/presentation/oferentes/cv")
    public String cv(Model model,HttpSession session) {
        model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
        return "presentation/oferentes/MiCV";
    }

}
