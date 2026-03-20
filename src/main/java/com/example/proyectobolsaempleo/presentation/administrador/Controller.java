package com.example.proyectobolsaempleo.presentation.administrador;

import com.example.proyectobolsaempleo.logic.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller("administrador")
public class Controller {
    @Autowired
    private ServiceAdmin serviceAdmin;
    @Autowired
    private ServiceOferente serviceOferente;
    @Autowired
    private ServiceEmpresa serviceEmpresa;
    @Autowired
    private ServiceCaracteristica serviceCaracteristica;
    @Autowired
    private ServiceReporte serviceReporte;


    // Dashboard
    @GetMapping("/presentation/administrador/dashboard")
    public String show(Model model, HttpSession session) {
        var user = session.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
            return "presentation/administrador/Dashboard";
        } else {
            return "presentation/partePublica/Puestosrecienregistrados";
        }
    }

    // AdminCaracteristicas
    @GetMapping("/presentation/administrador/AdminCaracteristicas")
    public String AdminCaracteristicas(Model model, HttpSession session,
                                       @RequestParam(required = false) Integer actualId) {
        var user = session.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));

            if (actualId == null) {
                model.addAttribute("caracteristicas", serviceCaracteristica.getRaices());
                model.addAttribute("ruta", new ArrayList<>());
                model.addAttribute("actual", null);
            } else {
                Caracteristica actual = serviceCaracteristica.findById(actualId);
                model.addAttribute("caracteristicas", serviceCaracteristica.getHijos(actualId));
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
                model.addAttribute("todas", serviceCaracteristica.getRaices());
            } else {
                model.addAttribute("todas", serviceCaracteristica.getHijos(actualId));
            }
            return "presentation/administrador/AdministradorCaracteristicas";
        } else {
            return "presentation/partePublica/Puestosrecienregistrados";
        }
    }

    @PostMapping("/presentation/administrador/crearCaracteristica")
    public String crearCaracteristica(@RequestParam String nombre,
                                      @RequestParam(required = false) Integer idPadre,
                                      @RequestParam(required = false) Integer actualId) {
        serviceCaracteristica.crearCaracteristica(nombre, idPadre);
        if (actualId != null) {
            return "redirect:/presentation/administrador/AdminCaracteristicas?actualId=" + actualId;
        }
        return "redirect:/presentation/administrador/AdminCaracteristicas";
    }

    // AdminEmpresasPendientes
    @GetMapping("/presentation/administrador/AdminEmpresasPendientes")
    public String AdminEmpresasPendientes(Model model, HttpSession session) {
        var user = session.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
            model.addAttribute("empresas", serviceEmpresa.empresasPendientes());
            return "presentation/administrador/AdministradorEmpresasPendientes";
        } else {
            return "presentation/partePublica/Puestosrecienregistrados";
        }
    }

    // Aprobar Empresa
    @PostMapping("/presentation/administrador/aprobarEmpresa")
    public String aprobarEmpresa(@RequestParam String id) {
        serviceEmpresa.aprobarEmpresa(id);
        return "redirect:/presentation/administrador/AdminEmpresasPendientes";
    }

    // AdminOferentesPendientes
    @GetMapping("/presentation/administrador/AdminOferentesPendientes")
    public String AdminOferentesPendientes(Model model, HttpSession session) {
        var user = session.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
            model.addAttribute("oferentes", serviceOferente.oferentesPendientes());
            return "presentation/administrador/AdministradorOferentesPendientes";
        } else {
            return "presentation/partePublica/Puestosrecienregistrados";
        }
    }

    // Aprobar Oferente
    @PostMapping("/presentation/administrador/aprobarOferente")
    public String aprobarOferente(@RequestParam String id) {
        serviceOferente.aprobarOferente(id);
        return "redirect:/presentation/administrador/AdminOferentesPendientes";
    }

    // Página del reporte
    @GetMapping("/presentation/administrador/Reportes")
    public String reportes(Model model, HttpSession session) {
        var user = session.getAttribute("usuario");
        if (user != null) {
            model.addAttribute("correoUsuario", session.getAttribute("correoUsuario"));
            model.addAttribute("mesActual", java.time.LocalDate.now().getMonthValue());
            model.addAttribute("anioActual", java.time.LocalDate.now().getYear());
            return "presentation/administrador/Reportes";
        } else {
            return "presentation/partePublica/Puestosrecienregistrados";
        }
    }

    // Descarga el PDF
    @GetMapping("/presentation/administrador/Reportes/pdf")
    public ResponseEntity<byte[]> descargarReporte(@RequestParam int mes,
                                                   @RequestParam int anio,
                                                   HttpSession session) {
        var user = session.getAttribute("usuario");
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        try {
            byte[] pdf = serviceReporte.generarReportePuestosPorMes(mes, anio);
            String nombreArchivo = "reporte-puestos-" + anio + "-" + String.format("%02d", mes) + ".pdf";
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + nombreArchivo + "\"")
                    .body(pdf);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
