package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.data.AdminRepository;

import java.util.LinkedList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service("serviceAdministrador")
public class ServiceAdministrador {

    @Autowired
    private AdminRepository adminRepository;

    public List<Administrador> listar() {
        return (List<Administrador>) adminRepository.findAll();
    }

    public Administrador buscarPorId(String id) {
        return adminRepository.findById(id).orElse(null);
    }
    public void guardar(Administrador administrador) {
        adminRepository.save(administrador);
    }
}
