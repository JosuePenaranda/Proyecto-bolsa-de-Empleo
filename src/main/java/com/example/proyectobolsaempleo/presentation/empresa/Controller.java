package com.example.proyectobolsaempleo.presentation.empresa;

import com.example.proyectobolsaempleo.logic.ServiceEmpresa;
import com.example.proyectobolsaempleo.logic.ServiceLogin;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller("empresas")
public class Controller {
    @Autowired
    private ServiceEmpresa serviceEmpresa;

    // Dashboard
    @GetMapping("/presentation/empresa/dashboard")
    public String show(Model model, HttpSession session) {
        var user = session.getAttribute("usuario");;
        if(user != null ) {
            model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
            return "presentation/empresa/Dashboard";
        } else {
            return "presentation/partePublica/Puestosrecienregistrados";
        }
    }

    // BuscarCandidatos
    @GetMapping("/presentation/empresa/BuscarCandidatos")
    public String BuscarCandidatos(Model model, HttpSession session) {
        var user = session.getAttribute("usuario");;
        if(user != null ) {
            model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
            return "presentation/empresa/BuscarCandidatos";
        } else {
            return "presentation/partePublica/Puestosrecienregistrados";
        }

    }

    // MisPuestos
    @GetMapping("/presentation/empresa/MisPuestos")
    public String MisPuestos(Model model, HttpSession session) {
        var user = session.getAttribute("usuario");
        if(user != null ) {
            model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
            return "presentation/empresa/MisPuestos";
        } else {
            return "presentation/partePublica/Puestosrecienregistrados";
        }
    }

    // VerDetalle
    @GetMapping("/presentation/empresa/VerDetalle")
    public String VerDetalle(Model model, HttpSession session) {
        var user = session.getAttribute("usuario");
        if(user != null ) {
            model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
            return "presentation/empresa/VerDetalle";
        } else {
            return "presentation/partePublica/Puestosrecienregistrados";
        }
    }
}
