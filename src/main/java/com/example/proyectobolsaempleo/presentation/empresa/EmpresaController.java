package com.example.proyectobolsaempleo.presentation.empresa;

import java.util.*;

import com.example.proyectobolsaempleo.Services.TipoCambioService;
import com.example.proyectobolsaempleo.Util.PasswordUtil;
import com.example.proyectobolsaempleo.logic.*;
import com.example.proyectobolsaempleo.modelo.ModeloDatos;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

@Controller
public class EmpresaController {

    @Autowired
    private HttpSession sesion;

    private ModeloDatos gestorDatos = ModeloDatos.getInstancia();

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
        model.addAttribute("puestos", gestorDatos.getServiceDatos().getServicePuesto().getTop5Publicos());

        TipoCambio tipoCambio = TipoCambioService.obtenerTipoCambio();
        model.addAttribute("tipoCambio", tipoCambio);

        return "presentation/partePublica/Puestosrecienregistrados";
    }

    @GetMapping("/empresa/Buscarpuesto")
    public String mostrarBusqueda(Model model) {
        model.addAttribute("raices", gestorDatos.getServiceDatos().getServiceCaracteristica().getRaices());
        model.addAttribute("caracteristicas", gestorDatos.getServiceDatos().getServiceCaracteristica().getTodas());
        model.addAttribute("puestos", null);
        return "presentation/partePublica/BuscarPuestos";
    }

    @GetMapping("/empresa/Buscarpuesto/buscar")
    public String buscarPuestos(@RequestParam(required = false) List<Integer> caracteristicas,
                                Model model) {
        TipoCambio tipoCambio = TipoCambioService.obtenerTipoCambio();
        model.addAttribute("tipoCambio", tipoCambio);
        model.addAttribute("raices", gestorDatos.getServiceDatos().getServiceCaracteristica().getRaices());
        model.addAttribute("caracteristicas", gestorDatos.getServiceDatos().getServiceCaracteristica().getTodas());
        model.addAttribute("seleccionadas", caracteristicas);
        model.addAttribute("puestos", gestorDatos.getServiceDatos().getServicePuesto().buscarPorCaracteristicas(caracteristicas));
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

        //Verificación para saber si ya fueron registrados, en dado caso despliega un mensaje de error

        if (gestorDatos.getServiceDatos().getServiceEmpresa().existeCorreo(correo)) {
            model.addAttribute("mensaje", "El correo ya está registrado");
            model.addAttribute("hayMensaje", 0);
            return "presentation/partePublica/RegistroEmpresa";
        }

        if (gestorDatos.getServiceDatos().getServiceEmpresa().existeNombre(nombre)) {
            model.addAttribute("mensaje", "El nombre de la empresa ya está registrado");
            model.addAttribute("hayMensaje", 0);
            return "presentation/partePublica/RegistroEmpresa";
        }

        try {
            gestorDatos.getServiceDatos().getServiceEmpresa().empresaSave(empresa);

        } catch (DataIntegrityViolationException e){ // Verificación adicional
            model.addAttribute("mensaje", "El correo, teléfono o nombre ya están registrados");
            model.addAttribute("hayMensaje", 0);
            return "presentation/partePublica/RegistroEmpresa";

        }
        model.addAttribute("mensaje", "Registro exitoso, espere aprobación del administrador");
        model.addAttribute("hayMensaje", 1);
        return "presentation/partePublica/RegistroEmpresa";
    }

    @GetMapping("/empresa/BuscarCandidatos")
    public String BuscarCandidatos(@RequestParam(required = false) Integer idPuesto, Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {

            List<Oferente> oferentes = gestorDatos.getServiceDatos().getServiceOferente().listar()
                    .stream()
                    .filter(o -> o.getAutorizado() != null && o.getAutorizado())
                    .toList();

            model.addAttribute("oferentes", oferentes);

            if (idPuesto != null) {
                Puesto puesto = gestorDatos.getServiceDatos().getServicePuesto().findById(idPuesto);
                List<PuestoRequisito> requisitos = gestorDatos.getServiceDatos().getServicePuesto().getRequisitosDePuesto(idPuesto);

                Map<String, Integer> cumplidos = new HashMap<>();
                Map<String, Double> porcentajes = new HashMap<>();

                List<Caracteristica> todas =
                        gestorDatos.getServiceDatos().getServiceCaracteristica().getHojas();

                // Vector del puesto
                List<Integer> vectorPuesto = construirVectorPuesto(puesto, todas);

                for (Oferente o : oferentes) {
                    cumplidos.put(o.getIdentificacion(), contarCumplidos(o, requisitos));

                    // Vector oferente
                    List<Integer> vectorOferente = construirVectorOferente(o, todas);

                    // Similitud coseno
                    double sim = gestorDatos.getServiceDatos().getServiceBusqueda().calcularSimilitudCoseno(vectorOferente, vectorPuesto);

                    porcentajes.put(o.getIdentificacion(), sim * 100);
                }

                model.addAttribute("puesto", puesto);
                model.addAttribute("totalRequisitos", requisitos.size());
                model.addAttribute("cumplidos", cumplidos);
                model.addAttribute("porcentajes", porcentajes);
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
            model.addAttribute("puestos", gestorDatos.getServiceDatos().getServicePuesto().getPuestosPorEmpresa(empresa));
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            return "presentation/empresa/MisPuestos";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    @PostMapping("/empresa/DesactivarPuesto")
    public String desactivarPuesto(@RequestParam Integer id) {
        if (sesion.getAttribute("usuario") == null) return "redirect:/empresa/Puestosrecienregistrados";
        gestorDatos.getServiceDatos().getServicePuesto().desactivarPuesto(id);
        return "redirect:/empresa/MisPuestos";
    }

    @PostMapping("/empresa/ActivarPuesto")
    public String activarPuesto(@RequestParam Integer id) {
        if (sesion.getAttribute("usuario") == null) return "redirect:/empresa/Puestosrecienregistrados";
        gestorDatos.getServiceDatos().getServicePuesto().activarPuesto(id);
        return "redirect:/empresa/MisPuestos";
    }

    @GetMapping("/empresa/PublicarPuesto")
    public String mostrarFormularioPuesto(@RequestParam(required = false) Integer cantidad, Model model) {
        var user = sesion.getAttribute("usuario");

        if (user != null) {
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            model.addAttribute("caracteristicas",
                    gestorDatos.getServiceDatos().getServiceCaracteristica().getHojas());
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

        if (sesion.getAttribute("usuario") == null) return "redirect:/empresa/Puestosrecienregistrados";

        // Validar repetidas
        Set<Integer> sinRepetidas = new HashSet<>(caracteristicas);
        if (sinRepetidas.size() != caracteristicas.size()) {
            model.addAttribute("error", "No puede repetir características");
            model.addAttribute("cantidad", caracteristicas.size());
            model.addAttribute("caracteristicas", gestorDatos.getServiceDatos().getServiceCaracteristica().getHojas());
            return "presentation/empresa/PublicarPuesto";
        }

        Empresa empresa = (Empresa) sesion.getAttribute("usuario");

        Puesto p = new Puesto();
        p.setDescripcion(descripcion);
        p.setSalario(salario);
        p.setTipoPublicacion(tipo);
        p.setIdEmpresa(empresa);
        p.setActivo(true);

        gestorDatos.getServiceDatos().getServicePuesto().guardarPuestoConRequisitos(p, caracteristicas, niveles);

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
                Oferente oferente = gestorDatos.getServiceDatos().getServiceOferente().buscarPorId(idOferente);
                List<Habilidad> habilidades = gestorDatos.getServiceDatos().getServiceHabilidad().listarPorOferente(oferente);
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

    // Crea un vector con las características y niveles del oferente

    private List<Integer> construirVectorOferente(Oferente o, List<Caracteristica> todas) {
        List<Integer> vector = new ArrayList<>();

        for (Caracteristica c : todas) {
            int nivel = 0;

            for (Habilidad h : o.getHabilidads()) {
                if (h.getIdCaracteristica().getId().equals(c.getId())) {
                    nivel = h.getNivel() != null ? h.getNivel() : 0;
                    break;
                }
            }

            vector.add(nivel);
        }

        return vector;
    }

    // Crea un vector con las características y niveles del puesto

    private List<Integer> construirVectorPuesto(Puesto p, List<Caracteristica> todas) {
        List<Integer> vector = new ArrayList<>();

        for (Caracteristica c : todas) {
            int nivel = 0;

            for (PuestoRequisito r : p.getPuestoRequisitos()) {
                if (r.getIdCaracteristica().getId().equals(c.getId())) {
                    nivel = r.getNivel() != null ? r.getNivel() : 0;
                    break;
                }
            }

            vector.add(nivel);
        }

        return vector;
    }
}