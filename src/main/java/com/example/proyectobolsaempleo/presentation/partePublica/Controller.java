package com.example.proyectobolsaempleo.presentation.partePublica;

import com.example.proyectobolsaempleo.Util.PasswordUtil;
import com.example.proyectobolsaempleo.logic.Oferente;
import com.example.proyectobolsaempleo.logic.ServiceOferente;
import com.example.proyectobolsaempleo.logic.ServicePartePublica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller("partePublica")
public class Controller {
    @Autowired
    private ServicePartePublica servicePartePublica;
    @Autowired
    private ServiceOferente serviceOferente;

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

    // Guardar Oferente
    @PostMapping("/presentation/partePublica/Registrooferente")
    public String registrar(@RequestParam String identificacion,
                            @RequestParam String nombre,
                            @RequestParam String primerApellido,
                            @RequestParam String nacionalidad,
                            @RequestParam Integer telefono,
                            @RequestParam String correo,
                            @RequestParam String lugarResidencia,
                            @RequestParam String clave,
                            Model model) {

        var oferente = new Oferente();
        oferente.setIdentificacion(identificacion);
        oferente.setNombre(nombre);
        oferente.setPrimerApellido(primerApellido);
        oferente.setNacionalidad(nacionalidad);
        oferente.setTelefono(telefono);
        oferente.setCorreo(correo);
        oferente.setLugarResidencia(lugarResidencia);
        oferente.setClave(PasswordUtil.hashPassword(clave));
        oferente.setAutorizado(false);
        oferente.setCurriculum(null);

        serviceOferente.oferenteSave(oferente);

        model.addAttribute("mensaje", "Registro exitoso, espere aprobación del administrador");
        return "presentation/partePublica/RegistroOferente";
    }
}
