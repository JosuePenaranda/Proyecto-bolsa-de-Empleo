package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.Util.PasswordUtil;
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
            admin.setClave(PasswordUtil.hashPassword(clave));
            return admin;
        }

        // Buscar admin
        Administrador admin = adminRepository.findByCorreo(correo);
        if(admin != null && PasswordUtil.verificarPassword(clave, admin.getClave())) return admin;

        // Buscar empresa
        Empresa empresa = empresaRepository.findByCorreo(correo);
        if(empresa != null && PasswordUtil.verificarPassword(clave, empresa.getClave())) return empresa;

        // Buscar oferente
        Oferente oferente = oferenteRepository.findByCorreo(correo);
        if(oferente != null && PasswordUtil.verificarPassword(clave, oferente.getClave())) return oferente;

        return null;
    }
}
