package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.Util.PasswordUtil;
import com.example.proyectobolsaempleo.data.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
