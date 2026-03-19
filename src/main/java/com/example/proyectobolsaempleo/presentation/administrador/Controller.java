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
        var user = session.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
            return "presentation/administrador/Dashboard";
        } else {
            return "presentation/partePublica/Puestosrecienregistrados";
        }
    }

    // AdminCaracteristicas
    @GetMapping("/presentation/administrador/AdminCaracteristicas")
    public String AdminCaracteristicas(Model model, HttpSession session) {
        var user = session.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
            return "presentation/administrador/AdministradorCaracteristicas";
        } else {
            return "presentation/partePublica/Puestosrecienregistrados";
        }
    }

    // AdminEmpresasPendientes
    @GetMapping("/presentation/administrador/AdminEmpresasPendientes")
    public String AdminEmpresasPendientes(Model model, HttpSession session) {
        var user = session.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
            return "presentation/administrador/AdministradorEmpresasPendientes";
        } else {
            return "presentation/partePublica/Puestosrecienregistrados";
        }
    }

    // AdminOferentesPendientes
    @GetMapping("/presentation/administrador/AdminOferentesPendientes")
    public String AdminOferentesPendientes(Model model, HttpSession session) {
        var user = session.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
            return "presentation/administrador/AdministradorOferentesPendientes";
        } else {
            return "presentation/partePublica/Puestosrecienregistrados";
        }
    }
}
