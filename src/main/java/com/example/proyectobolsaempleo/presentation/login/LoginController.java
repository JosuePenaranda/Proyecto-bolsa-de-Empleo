package com.example.proyectobolsaempleo.presentation.login;

import com.example.proyectobolsaempleo.logic.Administrador;
import com.example.proyectobolsaempleo.logic.Empresa;
import com.example.proyectobolsaempleo.logic.Oferente;
import com.example.proyectobolsaempleo.logic.ServiceLogin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller("login")
public class LoginController {
    @Autowired
    private ServiceLogin serviceLogin;

    // Login
    @GetMapping("/Login")
    public String show(Model model) {
        return "presentation/login/Login";
    }

    @PostMapping("/Login")
    public String validarLogin(HttpServletRequest req, @RequestParam String correo, @RequestParam String clave, Model model) {

        if(correo.isEmpty() || clave.isEmpty()) {
            model.addAttribute("error", "Campos vacíos");
            model.addAttribute("hayError", 1);
            return "presentation/login/Login";
        }

        var usuario = serviceLogin.login(correo, clave);

        if(usuario != null) {

            // Verificar autorización para empresa y oferente
            if(usuario instanceof Empresa empresa && !empresa.getAutorizado()) {
                model.addAttribute("error", "Su cuenta aún no ha sido autorizada");
                model.addAttribute("hayError", 1);
                return "presentation/login/Login";
            }

            if(usuario instanceof Oferente oferente && !oferente.getAutorizado()) {
                model.addAttribute("error", "Su cuenta aún no ha sido autorizada");
                model.addAttribute("hayError", 1);
                return "presentation/login/Login";
            }

            req.getSession().setAttribute("usuario", usuario);
            req.getSession().setAttribute("correoUsuario", correo);

            // Redirección dependiendo del tipo de usuario
            if(usuario instanceof Administrador) {
                return "redirect:/administrador/dashboard";
            }
            else if(usuario instanceof Empresa){
                return "redirect:/empresa/dashboard";
            }
            else {
                return "redirect:/oferentes/dashboard";
            }
        }
        else {
            model.addAttribute("error", "Usuario y/o contraseña incorrectos");
            model.addAttribute("hayError", 1);
            return "presentation/login/Login";
        }
    }

    @GetMapping("/salir")
    public String salir(HttpSession session) {
        session.removeAttribute("usuario");
        session.removeAttribute("correoUsuario");
        return "redirect:/empresa/Puestosrecienregistrados";
    }

}