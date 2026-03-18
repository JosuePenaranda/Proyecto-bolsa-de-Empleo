package com.example.proyectobolsaempleo.presentation.login;

import com.example.proyectobolsaempleo.logic.Administrador;
import com.example.proyectobolsaempleo.logic.Empresa;
import com.example.proyectobolsaempleo.logic.Oferente;
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
    @GetMapping("/presentation/login/login")
    public String show(Model model) {
        return "presentation/login/Login";
    }

    @PostMapping("/login")
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
                return "redirect:/administrador/dashboard";
            }
            else if(usuario instanceof Empresa){
                return "redirect:/empresa/dashboard";
            }
            else {
                return "redirect:/oferente/dashboard";
            }
        }
        else {
            model.addAttribute("error", "Usuario y/o contraseña incorrectos");
            return "presentation/login/Login";
        }
    }

    // Página después del login
    @GetMapping("index")
    public String index(Model modelo) {
        modelo.addAttribute("primerValor", "Bienvenido");
        return "index";
    }
}