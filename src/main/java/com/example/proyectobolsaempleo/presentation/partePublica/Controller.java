package com.example.proyectobolsaempleo.presentation.partePublica;

import com.example.proyectobolsaempleo.logic.ServiceLogin;
import com.example.proyectobolsaempleo.logic.ServicePartePublica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller("partePublica")
public class Controller {
    @Autowired
    private ServicePartePublica servicePartePublica;

    // Inicializar la página
    @GetMapping("/presentation/partePublica/Puestosrecienregistrados")
    public String show(Model model) {
        return "presentation/partePublica/PuestosRecienRegistrados";
    }

    // BuscarPuestos
    @GetMapping("/presentation/partePublica/Buscarpuesto")
    public String buscarPuesto(Model model) {
        return "presentation/partePublica/BuscarPuestos";
    }

    // RegistroEmpresa
    @GetMapping("/presentation/partePublica/Registroempresa")
    public String registroEmpresa(Model model) {
        return "presentation/partePublica/RegistroEmpresa";
    }

    // RegistroOferente
    @GetMapping("/presentation/partePublica/Registrooferente")
    public String registroOferente(Model model) { return "presentation/partePublica/RegistroOferente";}

    // Login
    @GetMapping("/presentation/partePublica/login")
    public String Login(Model model) { return "presentation/login/Login";}

}
