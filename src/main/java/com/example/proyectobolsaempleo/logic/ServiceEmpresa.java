package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.data.*;
import org.springframework.beans.factory.annotation.*;

import java.util.List;

@org.springframework.stereotype.Service("serviceEmpresa")
public class ServiceEmpresa {
    @Autowired
    private EmpresaRepository empresaRepository;

    public void empresaSave(Empresa empresa) {
        empresaRepository.save(empresa);
    }

    public List<Empresa> empresasPendientes() {
        return empresaRepository.findByAutorizado(false);
    }

    public void aprobarEmpresa(String id) {
        Empresa empresa = empresaRepository.findById(id).orElse(null);
        if (empresa != null) {
            empresa.setAutorizado(true);
            empresaRepository.save(empresa);
        }
    }

    public List<Empresa> listar() {
        return (List<Empresa>) empresaRepository.findAll();
    }

    public boolean existeCorreo(String correo) {
        return empresaRepository.existsByCorreo(correo);
    }

    public boolean existeTelefono(String telefono) {
        return empresaRepository.existsByTelefono(telefono);
    }

    public boolean existeNombre(String nombre) {
        return empresaRepository.existsByNombre(nombre);
    }
}
