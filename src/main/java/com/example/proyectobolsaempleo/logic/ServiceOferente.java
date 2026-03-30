package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.data.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@org.springframework.stereotype.Service("serviceOferente")
public class ServiceOferente {
    @Autowired
    private OferenteRepository oferenteRepository;

    public void oferenteSave(Oferente oferente) {
        oferenteRepository.save(oferente);
    }

    public List<Oferente> oferentesPendientes() {
        return oferenteRepository.findByAutorizado(false);
    }

    public void aprobarOferente(String id) {
        Oferente oferente = oferenteRepository.findById(id).orElse(null);
        if (oferente != null) {
            oferente.setAutorizado(true);
            oferenteRepository.save(oferente);
        }
    }

    public List<Oferente> listar() {
        return (List<Oferente>) oferenteRepository.findAll();
    }

    public void guardarCurriculum(String identificacion, MultipartFile archivo) throws IOException {
        String nombreArchivo = identificacion + ".pdf";
        Path ruta = Paths.get("uploads/cvs/" + nombreArchivo);
        Files.createDirectories(ruta.getParent());
        Files.write(ruta, archivo.getBytes());

        Oferente oferente = oferenteRepository.findById(identificacion).orElse(null);
        if (oferente != null) {
            oferente.setCurriculum("uploads/cvs/" + nombreArchivo);
            oferenteRepository.save(oferente);
        }
    }

    public Oferente buscarPorId(String id) {
        return oferenteRepository.findById(id).orElse(null);
    }

    public boolean existeCorreo(String correo) {
        return oferenteRepository.existsByCorreo(correo);
    }

    public boolean existeTelefono(String telefono) {
        return oferenteRepository.existsByTelefono(telefono);
    }

    public boolean existeIdentificacion(String identificacion) {
        return oferenteRepository.existsByIdentificacion(identificacion);
    }
}
