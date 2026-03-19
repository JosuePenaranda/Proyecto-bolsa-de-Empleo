package com.example.proyectobolsaempleo.presentation.administrador;

import com.example.proyectobolsaempleo.logic.ServiceAdmin;
import com.example.proyectobolsaempleo.logic.ServiceLogin;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller("administrador")
public class Controller {
    @Autowired
    private ServiceAdmin serviceAdmin;


    // Dashboard
    @GetMapping("/presentation/administrador/dashboard")
    public String show(Model model, HttpSession session) {
        model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
        return "presentation/administrador/Dashboard";
    }

    // AdminCaracteristicas
    @GetMapping("/presentation/administrador/AdminCaracteristicas")
    public String AdminCaracteristicas(Model model, HttpSession session) {
        model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
        return "presentation/administrador/AdministradorCaracteristicas";
    }

    // AdminEmpresasPendientes
    @GetMapping("/presentation/administrador/AdminEmpresasPendientes")
    public String AdminEmpresasPendientes(Model model, HttpSession session) {
        model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
        return "presentation/administrador/AdministradorEmpresasPendientes";
    }

    // AdminOferentesPendientes
    @GetMapping("/presentation/administrador/AdminOferentesPendientes")
    public String AdminOferentesPendientes(Model model, HttpSession session) {
        model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
        return "presentation/administrador/AdministradorOferentesPendientes";
    }


}
