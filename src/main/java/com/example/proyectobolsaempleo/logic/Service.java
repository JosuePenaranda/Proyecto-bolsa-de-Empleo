package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.data.*;
import org.springframework.beans.factory.annotation.*;

@org.springframework.stereotype.Service("service")
public class Service {
    @Autowired
    private OferenteRepository oferenteRepository;

    // Buscar oferente por ID
    public Oferente oferenteFindById(String id) {
        return oferenteRepository.findById(id).orElse(null);
    }

    // Guardar/actualizar oferente
    public void oferenteSave(Oferente oferente) {
        oferenteRepository.save(oferente);
    }
}
