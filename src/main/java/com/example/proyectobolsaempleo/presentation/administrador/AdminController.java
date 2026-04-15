package com.example.proyectobolsaempleo.presentation.administrador;

import com.example.proyectobolsaempleo.logic.*;
import com.example.proyectobolsaempleo.modelo.ModeloDatos;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

@Controller
public class AdminController {

    @Autowired
    private HttpSession sesion;

    private ModeloDatos gestorDatos = ModeloDatos.getInstancia();

    // Dashboard
    @GetMapping("/administrador/dashboard")
    public String show(Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            return "presentation/administrador/Dashboard";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    // AdminCaracteristicas
    @GetMapping("/administrador/AdminCaracteristicas")
    public String AdminCaracteristicas(Model model,
                                       @RequestParam(required = false) Integer actualId) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));

            if (actualId == null) {
                model.addAttribute("caracteristicas",
                        gestorDatos.getServiceDatos().getServiceCaracteristica().getRaices());
                model.addAttribute("ruta", new ArrayList<>());
                model.addAttribute("actual", null);
            } else {
                Caracteristica actual =
                        gestorDatos.getServiceDatos().getServiceCaracteristica().findById(actualId);

                model.addAttribute("caracteristicas",
                        gestorDatos.getServiceDatos().getServiceCaracteristica().getHijos(actualId));
                model.addAttribute("actual", actual);

                List<Caracteristica> ruta = new ArrayList<>();
                Caracteristica temp = actual;

                while (temp != null) {
                    ruta.add(0, temp);
                    temp = temp.getIdPadre();
                }
                model.addAttribute("ruta", ruta);
            }

            if (actualId == null) {
                model.addAttribute("todas",
                        gestorDatos.getServiceDatos().getServiceCaracteristica().getRaices());
            } else {
                model.addAttribute("todas",
                        gestorDatos.getServiceDatos().getServiceCaracteristica().getHijos(actualId));
            }

            return "presentation/administrador/AdministradorCaracteristicas";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    @PostMapping("/administrador/crearCaracteristica")
    public String crearCaracteristica(@RequestParam String nombre,
                                      @RequestParam(required = false) Integer idPadre,
                                      @RequestParam(required = false) Integer actualId) {
        if (sesion.getAttribute("usuario") == null) return "redirect:/empresa/Puestosrecienregistrados";
        gestorDatos.getServiceDatos().getServiceCaracteristica()
                .crearCaracteristica(nombre, idPadre);

        if (actualId != null) {
            return "redirect:/administrador/AdminCaracteristicas?actualId=" + actualId;
        }
        return "redirect:/administrador/AdminCaracteristicas";
    }

    // AdminEmpresasPendientes
    @GetMapping("/administrador/AdminEmpresasPendientes")
    public String AdminEmpresasPendientes(Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {

                model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
                model.addAttribute("empresas",
                        gestorDatos.getServiceDatos().getServiceEmpresa().empresasPendientes());

                return "presentation/administrador/AdministradorEmpresasPendientes";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    // Aprobar Empresa
    @PostMapping("/administrador/aprobarEmpresa")
    public String aprobarEmpresa(@RequestParam String id) {
        if (sesion.getAttribute("usuario") == null) return "redirect:/empresa/Puestosrecienregistrados";
        gestorDatos.getServiceDatos().getServiceEmpresa().aprobarEmpresa(id);

        return "redirect:/administrador/AdminEmpresasPendientes";
    }

    // AdminOferentesPendientes
    @GetMapping("/administrador/AdminOferentesPendientes")
    public String AdminOferentesPendientes(Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {

            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            model.addAttribute("oferentes",
                    gestorDatos.getServiceDatos().getServiceOferente().oferentesPendientes());

            return "presentation/administrador/AdministradorOferentesPendientes";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    // Aprobar Oferente
    @PostMapping("/administrador/aprobarOferente")
    public String aprobarOferente(@RequestParam String id) {
        if (sesion.getAttribute("usuario") == null) return "redirect:/empresa/Puestosrecienregistrados";
        gestorDatos.getServiceDatos().getServiceOferente().aprobarOferente(id);

        return "redirect:/administrador/AdminOferentesPendientes";
    }

    @GetMapping("/administrador/Reportes")
    public String reportes(@RequestParam(required = false) Integer mes,
                           @RequestParam(required = false) Integer anio,
                           Model model) {
        var user = sesion.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", sesion.getAttribute("correoUsuario"));
            model.addAttribute("mesActual", mes != null ? mes : java.time.LocalDate.now().getMonthValue());
            model.addAttribute("anioActual", anio != null ? anio : java.time.LocalDate.now().getYear());

            if (mes != null && anio != null) {
                model.addAttribute("urlReporte", "/administrador/Reportes/pdf?mes=" + mes + "&anio=" + anio);
            }

            return "presentation/administrador/Reportes";
        } else {
            return "redirect:/empresa/Puestosrecienregistrados";
        }
    }

    // Descarga el PDF
    @GetMapping("/administrador/Reportes/pdf")
    public ResponseEntity<byte[]> descargarReporte(@RequestParam int mes,
                                                   @RequestParam int anio) {
        var user = sesion.getAttribute("usuario");
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        try {
            byte[] pdf = gestorDatos.getServiceDatos().getServiceReporte()
                    .generarReportePuestosPorMes(mes, anio);

            String nombreArchivo = "reporte-puestos-" + anio + "-" + String.format("%02d", mes) + ".pdf";

            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + nombreArchivo + "\"")
                    .body(pdf);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}