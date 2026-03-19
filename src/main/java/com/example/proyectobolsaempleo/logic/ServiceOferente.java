package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.Util.PasswordUtil;
import com.example.proyectobolsaempleo.data.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Service("serviceOferente")
public class ServiceOferente {
    @Autowired
    private OferenteRepository oferenteRepository;

    public void oferenteSave(Oferente oferente) {
        oferenteRepository.save(oferente);
    }
}
