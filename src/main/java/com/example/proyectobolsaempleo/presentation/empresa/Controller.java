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
    @GetMapping("/presentation/empresa/dashboard")
    public String show(Model model) {
        return "presentation/empresa/Dashboard";
    }

    // BuscarCandidatos
    @GetMapping("/presentation/empresa/BuscarCandidatos")
    public String BuscarCandidatos(Model model) {
        return "presentation/empresa/BuscarCandidatos";
    }

    // MisPuestos
    @GetMapping("/presentation/empresa/MisPuestos")
    public String MisPuestos(Model model) {
        return "presentation/empresa/MisPuestos";
    }

    // VerDetalle
    @GetMapping("/presentation/empresa/VerDetalle")
    public String VerDetalle(Model model) {
        return "presentation/empresa/VerDetalle";
    }

}
