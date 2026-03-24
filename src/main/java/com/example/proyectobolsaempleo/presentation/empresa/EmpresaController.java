package com.example.proyectobolsaempleo.presentation.empresa;

import java.util.HashMap;
import java.util.Map;
import com.example.proyectobolsaempleo.Services.TipoCambioService;
import com.example.proyectobolsaempleo.Util.PasswordUtil;
import com.example.proyectobolsaempleo.logic.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class EmpresaController {

    @Autowired
    private HttpSession sesion;

    @Autowired
    private ServiceDatos gestorDatos;

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

    @GetMapping("/empresa/Puestosrecienregistrados")
    public String puestosRecientes(Model model) {
        model.addAttribute("puestos", gestorDatos.getServicePuesto().getTop5Publicos());

        TipoCambio tipoCambio = TipoCambioService.obtenerTipoCambio();
        model.addAttribute("tipoCambio", tipoCambio);

        return "presentation/partePublica/Puestosrecienregistrados";
    }

    @GetMapping("/empresa/Buscarpuesto")
    public String mostrarBusqueda(Model model) {
        model.addAttribute("raices", gestorDatos.getServiceCaracteristica().getRaices());
        model.addAttribute("caracteristicas", gestorDatos.getServiceCaracteristica().getTodas());
        model.addAttribute("puestos", null);
        return "presentation/partePublica/BuscarPuestos";
    }

    @GetMapping("/empresa/Buscarpuesto/buscar")
    public String buscarPuestos(@RequestParam(required = false) List<Integer> caracteristicas,
                                Model model) {
        TipoCambio tipoCambio = TipoCambioService.obtenerTipoCambio();
        model.addAttribute("tipoCambio", tipoCambio);
        model.addAttribute("raices", gestorDatos.getServiceCaracteristica().getRaices());
        model.addAttribute("caracteristicas", gestorDatos.getServiceCaracteristica().getTodas());
        model.addAttribute("seleccionadas", caracteristicas);
        model.addAttribute("puestos", gestorDatos.getServicePuesto().buscarPorCaracteristicas(caracteristicas));
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

        gestorDatos.getServiceEmpresa().empresaSave(empresa);

        model.addAttribute("mensaje", "Registro exitoso, espere aprobación del administrador");
        model.addAttribute("hayMensaje", 1);
        return "presentation/partePublica/RegistroEmpresa";
    }

    @GetMapping("/empresa/BuscarCandidatos")
    public String BuscarCandidatos(@RequestParam(required = false) Integer idPuesto, Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {

            List<Oferente> oferentes = gestorDatos.getServiceOferente().listar()
                    .stream()
                    .filter(o -> o.getAutorizado() != null && o.getAutorizado())
                    .toList();

            model.addAttribute("oferentes", oferentes);

            if (idPuesto != null) {
                Puesto puesto = gestorDatos.getServicePuesto().findById(idPuesto);
                List<PuestoRequisito> requisitos = gestorDatos.getServicePuesto().getRequisitosDePuesto(idPuesto);

                Map<String, Integer> cumplidos = new HashMap<>();
                for (Oferente o : oferentes) {
                    cumplidos.put(o.getIdentificacion(), contarCumplidos(o, requisitos));
                }

                model.addAttribute("puesto", puesto);
                model.addAttribute("totalRequisitos", requisitos.size());
                model.addAttribute("cumplidos", cumplidos);
            }

            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            return "presentation/empresa/BuscarCandidatos";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    private int contarCumplidos(Oferente oferente, List<PuestoRequisito> requisitos) {
        List<Integer> idsOferente = oferente.getHabilidads().stream()
                .map(h -> h.getIdCaracteristica().getId())
                .toList();
        return (int) requisitos.stream()
                .filter(r -> idsOferente.contains(r.getIdCaracteristica().getId()))
                .count();
    }

    @GetMapping("/empresa/MisPuestos")
    public String MisPuestos(Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {
            Empresa empresa = (Empresa) sesion.getAttribute("usuario");
            model.addAttribute("puestos", gestorDatos.getServicePuesto().getPuestosPorEmpresa(empresa));
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            return "presentation/empresa/MisPuestos";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    @PostMapping("/empresa/DesactivarPuesto")
    public String desactivarPuesto(@RequestParam Integer id) {
        gestorDatos.getServicePuesto().desactivarPuesto(id);
        return "redirect:/empresa/MisPuestos";
    }

    @PostMapping("/empresa/ActivarPuesto")
    public String activarPuesto(@RequestParam Integer id) {
        gestorDatos.getServicePuesto().activarPuesto(id);
        return "redirect:/empresa/MisPuestos";
    }

    @GetMapping("/empresa/PublicarPuesto")
    public String mostrarFormularioPuesto(@RequestParam(required = false) Integer cantidad, Model model) {
        var user = sesion.getAttribute("usuario");

        if (user != null) {
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            model.addAttribute("caracteristicas",
                    gestorDatos.getServiceCaracteristica().getHojas());
            model.addAttribute("cantidad", cantidad);

            return "presentation/empresa/PublicarPuesto";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    @PostMapping("/empresa/PublicarPuesto")
    public String publicarPuesto(
            @RequestParam String descripcion,
            @RequestParam Double salario,
            @RequestParam String tipo,
            @RequestParam(value = "caracteristicas[]", required = false) List<Integer> caracteristicas,
            @RequestParam(value = "niveles[]", required = false) List<Integer> niveles,
            Model model) {

        // Validar repetidas
        Set<Integer> sinRepetidas = new HashSet<>(caracteristicas);
        if (sinRepetidas.size() != caracteristicas.size()) {
            model.addAttribute("error", "No puede repetir características");
            model.addAttribute("cantidad", caracteristicas.size());
            model.addAttribute("caracteristicas", gestorDatos.getServiceCaracteristica().getHojas());
            return "presentation/empresa/PublicarPuesto";
        }

        Empresa empresa = (Empresa) sesion.getAttribute("usuario");

        Puesto p = new Puesto();
        p.setDescripcion(descripcion);
        p.setSalario(salario);
        p.setTipoPublicacion(tipo);
        p.setIdEmpresa(empresa);
        p.setActivo(true);

        gestorDatos.getServicePuesto().guardarPuestoConRequisitos(p, caracteristicas, niveles);

        model.addAttribute("mensaje", "Puesto publicado correctamente");
        model.addAttribute("hayMensaje", 1);

        return "redirect:/empresa/PublicarPuesto";
    }

    @GetMapping("/empresa/VerDetalle")
    public String VerDetalle(@RequestParam(required = false) String idOferente,
                             @RequestParam(required = false) Integer idPuesto,
                             Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {
            if (idOferente != null) {
                Oferente oferente = gestorDatos.getServiceOferente().buscarPorId(idOferente);
                List<Habilidad> habilidades = gestorDatos.getServiceHabilidad().listarPorOferente(oferente);
                model.addAttribute("oferente", oferente);
                model.addAttribute("habilidades", habilidades);
            }
            model.addAttribute("idPuesto", idPuesto);
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            return "presentation/empresa/VerDetalles";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

}