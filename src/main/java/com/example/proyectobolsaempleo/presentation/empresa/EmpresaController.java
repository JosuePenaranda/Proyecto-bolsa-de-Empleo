package com.example.proyectobolsaempleo.presentation.empresa;

import com.example.proyectobolsaempleo.Util.PasswordUtil;
import com.example.proyectobolsaempleo.logic.Empresa;
import com.example.proyectobolsaempleo.logic.ServiceEmpresa;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller("empresa")
public class EmpresaController {

    @Autowired
    private HttpSession sesion;

    @Autowired
    private ServiceEmpresa serviceEmpresa;


    // Dashboard
    @GetMapping("/empresa/dashboard")
    public String show(Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            return "presentation/empresa/Dashboard";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    // Puestos recien registrados (Parte publica)
    @GetMapping("/empresa/Puestosrecienregistrados")
    public String Puestosrecienregistrados(Model model) {
        return "presentation/partePublica/PuestosRecienRegistrados";
    }

    // BuscarPuestos (Parte publica)
    @GetMapping("/empresa/Buscarpuesto")
    public String buscarPuesto(Model model) {
        return "presentation/partePublica/BuscarPuestos";
    }

    // RegistroEmpresa (Parte publica)
    @GetMapping("/empresa/Registroempresa")
    public String registroEmpresa(Model model) {
        return "presentation/partePublica/RegistroEmpresa";
    }

    // Guardar Empresa (Parte publica)
    @PostMapping("/empresa/Registroempresa")
    public String registrarEmpresa(@RequestParam String nombre,
                                   @RequestParam String localizacion,
                                   @RequestParam String correo,
                                   @RequestParam String telefono,
                                   @RequestParam String clave,
                                   @RequestParam String descripcion,
                                   Model model) {

        var empresa = new Empresa();
        empresa.setNombre(nombre);
        empresa.setLocalizacion(localizacion);
        empresa.setCorreo(correo);
        empresa.setTelefono(telefono);
        empresa.setClave(PasswordUtil.hashPassword(clave));
        empresa.setDescripcion(descripcion);
        empresa.setAutorizado(false);

        serviceEmpresa.empresaSave(empresa);

        model.addAttribute("mensaje", "Registro exitoso, espere aprobación del administrador");
        model.addAttribute("hayMensaje", 1);
        return "presentation/partePublica/RegistroEmpresa";
    }

    // BuscarCandidatos
    @GetMapping("/empresa/BuscarCandidatos")
    public String BuscarCandidatos(Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            return "presentation/empresa/BuscarCandidatos";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    // MisPuestos
    @GetMapping("/empresa/MisPuestos")
    public String MisPuestos(Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            return "presentation/empresa/MisPuestos";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    // VerDetalle
    @GetMapping("/empresa/VerDetalle")
    public String VerDetalle(Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            return "presentation/empresa/VerDetalle";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }
}