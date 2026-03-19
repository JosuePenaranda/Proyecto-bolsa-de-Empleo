package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.data.*;
import org.springframework.beans.factory.annotation.*;

@org.springframework.stereotype.Service("serviceLogin")
public class ServiceLogin {
    @Autowired
    private OferenteRepository oferenteRepository;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private AdminRepository adminRepository;

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
