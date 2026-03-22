package com.example.proyectobolsaempleo.presentation.oferentes;

import com.example.proyectobolsaempleo.Services.NacionalidadService;
import com.example.proyectobolsaempleo.Util.PasswordUtil;
import com.example.proyectobolsaempleo.logic.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@org.springframework.stereotype.Controller("oferentes")
public class OferenteController {

    @Autowired
    private HttpSession sesion;

    @Autowired
    private ServiceDatos gestorDatos;

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
    public String habilidades(@RequestParam(required = false) Integer actualId, Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {
            Oferente oferente = (Oferente) sesion.getAttribute("usuario");

            Caracteristica actual = actualId != null
                    ? gestorDatos.getServiceCaracteristica().findById(actualId)
                    : null;

            List<Caracteristica> hijos = gestorDatos.getServiceCaracteristica().getHijos(actualId);
            List<Caracteristica> ruta = gestorDatos.getServiceCaracteristica().obtenerRuta(actualId);
            List<Habilidad> habilidades = gestorDatos.getServiceHabilidad().listarPorOferente(oferente);

            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            model.addAttribute("actual", actual);
            model.addAttribute("caracteristicas", hijos);
            model.addAttribute("ruta", ruta);
            model.addAttribute("habilidades", habilidades);
            model.addAttribute("esHoja", hijos.isEmpty());

            return "presentation/oferentes/MisHabilidades";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    // Agregar Habilidad
    @PostMapping("/oferentes/habilidades")
    public String agregarHabilidad(@RequestParam Integer idCaracteristica,
                                   @RequestParam Integer nivel) {
        Oferente oferente = (Oferente) sesion.getAttribute("usuario");
        Caracteristica caracteristica = gestorDatos.getServiceCaracteristica().findById(idCaracteristica);

        // Validar que no exista ya
        if (gestorDatos.getServiceHabilidad().yaExiste(oferente, caracteristica)) {
            return "redirect:/oferentes/habilidades?actualId=" + idCaracteristica;
        }

        Habilidad h = new Habilidad();
        h.setIdOferente(oferente);
        h.setIdCaracteristica(caracteristica);
        h.setNivel(nivel);

        gestorDatos.getServiceHabilidad().guardar(h);

        return "redirect:/oferentes/habilidades";
    }

    // Actualizar Habilidad
    @PostMapping("/oferentes/actualizarHabilidad")
    public String actualizarHabilidad(@RequestParam Integer idHabilidad,
                                      @RequestParam Integer nivel) {
        gestorDatos.getServiceHabilidad().actualizar(idHabilidad, nivel);
        return "redirect:/oferentes/habilidades";
    }

    @GetMapping("/oferentes/cv")
    public String cv(Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {
            Oferente oferente = (Oferente) sesion.getAttribute("usuario");
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            model.addAttribute("curriculum", oferente.getCurriculum());
            return "presentation/oferentes/MiCV";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    // Subir CV
    @PostMapping("/oferentes/cv")
    public String subirCV(@RequestParam("archivo") MultipartFile archivo, Model model) {
        Oferente oferente = (Oferente) sesion.getAttribute("usuario");

        try {
            gestorDatos.getServiceOferente().guardarCurriculum(oferente.getIdentificacion(), archivo);

            // Actualizar el oferente en sesión
            Oferente actualizado = gestorDatos.getServiceOferente().buscarPorId(oferente.getIdentificacion());
            sesion.setAttribute("usuario", actualizado);

            model.addAttribute("mensaje", "CV subido correctamente");
        } catch (IOException e) {
            model.addAttribute("mensaje", "Error al subir el CV");
        }

        model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
        model.addAttribute("curriculum", gestorDatos.getServiceOferente().buscarPorId(oferente.getIdentificacion()).getCurriculum());
        return "presentation/oferentes/MiCV";
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