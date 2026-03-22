package com.example.proyectobolsaempleo.Services;

import com.example.proyectobolsaempleo.logic.Administrador;
import com.example.proyectobolsaempleo.logic.ServiceDatos;
import com.example.proyectobolsaempleo.Util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInicializer implements CommandLineRunner {

    @Autowired
    private ServiceDatos gestorDatos;

    @Override
    public void run(String... args) throws Exception {

        // Si ya hay un admin no hace nada
        if (gestorDatos.getServiceAdministrador().buscarPorId("admin") != null) {
            return;
        }

        // Si no hay ninguno lo crea
        Administrador admin = new Administrador();
        admin.setIdentificacion("admin");
        admin.setCorreo("admin@bolsaempleo.com");
        admin.setClave(PasswordUtil.hashPassword("admin123"));

        gestorDatos.getServiceAdministrador().guardar(admin);

        System.out.println("Admin creado: admin@bolsaempleo.com / admin123");
    }
}
