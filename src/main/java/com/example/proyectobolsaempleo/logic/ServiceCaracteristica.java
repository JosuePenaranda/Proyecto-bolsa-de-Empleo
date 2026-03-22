package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.data.CaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

@org.springframework.stereotype.Service("serviceCaracteristica")
public class ServiceCaracteristica {
    @Autowired
    private CaracteristicaRepository caracteristicaRepository;

    public List<Caracteristica> getRaices() {
        return caracteristicaRepository.findByIdPadreIsNull();
    }

    public List<Caracteristica> getHijos(Integer idPadre) {
        Caracteristica padre = caracteristicaRepository.findById(idPadre).orElse(null);
        return caracteristicaRepository.findByIdPadre(padre);
    }

    public List<Caracteristica> getTodas() {
        return (List<Caracteristica>) caracteristicaRepository.findAll();
    }

    public void crearCaracteristica(String nombre, Integer idPadre) {
        Caracteristica c = new Caracteristica();
        c.setNombre(nombre);
        if (idPadre != null) {
            Caracteristica padre = caracteristicaRepository.findById(idPadre).orElse(null);
            c.setIdPadre(padre);
        }
        caracteristicaRepository.save(c);
    }

    public Caracteristica findById(Integer id) {
        return caracteristicaRepository.findById(id).orElse(null);
    }

    public List<Caracteristica> getHojas() {
        List<Caracteristica> todas = (List<Caracteristica>) caracteristicaRepository.findAll();

        return todas.stream()
                .filter(c -> caracteristicaRepository.findByIdPadre(c).isEmpty())
                .toList();
    }

}
