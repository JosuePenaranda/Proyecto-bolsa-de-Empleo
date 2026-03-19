package com.example.proyectobolsaempleo.presentation.login;

import com.example.proyectobolsaempleo.logic.Administrador;
import com.example.proyectobolsaempleo.logic.Empresa;
import com.example.proyectobolsaempleo.logic.Service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller("login")
public class Controller {
    @Autowired
    private Service service;

    // Login
    @GetMapping("/presentation/login/Login")
    public String show(Model model) {
        return "presentation/login/Login";
    }

    @PostMapping("/presentation/login")
    public String validarLogin(HttpServletRequest req, @RequestParam String correo, @RequestParam String clave, Model model) {

        if(correo.isEmpty() || clave.isEmpty()) {
            model.addAttribute("error", "Campos vacíos");
            return "presentation/login/Login";
        }

        var usuario = service.login(correo, clave);

        if(usuario != null) {
            req.getSession().setAttribute("usuario", usuario);

            // Redirección dependiendo del tipo de usuario
            if(usuario instanceof Administrador) {
                return "redirect:/presentation/administrador/dashboard";
            }
            else if(usuario instanceof Empresa){
                return "redirect:/presentation/empresa/dashboard";
            }
            else {
                return "redirect:/presentation/oferentes/dashboard";
            }
        }
        else {
            model.addAttribute("error", "Usuario y/o contraseña incorrectos");
            return "presentation/login/Login";
        }
    }

}