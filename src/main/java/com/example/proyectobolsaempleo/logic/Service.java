package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.data.*;
import org.springframework.beans.factory.annotation.*;

@org.springframework.stereotype.Service("service")
public class Service {
    @Autowired
    private OferenteRepository oferenteRepository;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private AdminRepository adminRepository;

    // Buscar oferente por ID
    public Oferente oferenteFindById(String id) {
        return oferenteRepository.findById(id).orElse(null);
    }

    // Guardar/actualizar oferente
    public void oferenteSave(Oferente oferente) {
        oferenteRepository.save(oferente);
    }

    // Buscar
    public Object login(String correo, String clave) {

        // Administrador predeterminado
        if(correo.equals("admin@una.cr") && clave.equals("root")){
            Administrador admin = new Administrador();
            admin.setIdentificacion("1");
            admin.setCorreo(correo);
            admin.setClave(clave);
            return admin;
        }

        // Buscar admin
        Administrador admin = adminRepository.findByCorreoAndClave(correo, clave);
        if(admin != null) return admin;

        // Buscar empresa
        Empresa empresa = empresaRepository.findByCorreoAndClave(correo, clave);
        if(empresa != null) return empresa;

        // Buscar oferente
        Oferente oferente = oferenteRepository.findByCorreoAndClave(correo, clave);
        if(oferente != null) return oferente;

        return null;
    }
}
