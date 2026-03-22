package com.example.proyectobolsaempleo.presentation.oferentes;

import com.example.proyectobolsaempleo.Services.NacionalidadService;
import com.example.proyectobolsaempleo.Util.PasswordUtil;
import com.example.proyectobolsaempleo.logic.ModeloDatos;
import com.example.proyectobolsaempleo.logic.Nacionalidad;
import com.example.proyectobolsaempleo.logic.Oferente;
import com.example.proyectobolsaempleo.logic.ServiceOferente;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@org.springframework.stereotype.Controller("oferentes")
public class OferenteController {

    @Autowired
    private HttpSession sesion;

    @Autowired
    private ModeloDatos gestorDatos;

    // Dashboard
    @GetMapping("/oferentes/dashboard")
    public String show(Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            return "presentation/oferentes/Dashboard";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    // Mis Habilidades
    @GetMapping("/oferentes/habilidades")
    public String habilidades(Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            return "presentation/oferentes/MisHabilidades";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    // Mi CV
    @GetMapping("/oferentes/cv")
    public String cv(Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            return "presentation/oferentes/MiCV";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    // Registro Oferente (Parte Publica)
    @GetMapping("/oferentes/Registrooferente")
    public String mostrarFormulario(Model model) {
        List<Nacionalidad> nacionalidades = NacionalidadService.obtenerNacionalidades();
        model.addAttribute("nacionalidades", nacionalidades);
        return "presentation/partePublica/Registrooferente";
    }

    // Guardar Oferente (Parte Publica)
    @PostMapping("/oferentes/Registrooferente")
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

        gestorDatos.getServiceOferente().oferenteSave(oferente);

        model.addAttribute("mensaje", "Registro exitoso, espere aprobación del administrador");
        model.addAttribute("hayMensaje", 1);
        return "presentation/partePublica/Registrooferente";
    }
}