package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.data.PuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@org.springframework.stereotype.Service("servicePuesto")
public class ServicePuesto {

    @Autowired
    private PuestoRepository puestoRepository;

    public List<Puesto> getPuestosPorMes(int mes, int anio) {
        return puestoRepository.findByMesYAnio(mes, anio);
    }

    public List<PuestoRequisito> getRequisitosDePuesto(Integer idPuesto) {
        return puestoRepository.findRequisitorByPuesto(idPuesto);
    }

    public Puesto findById(Integer id) {
        return puestoRepository.findById(id).orElse(null);
    }

    public void guardarPuesto(Puesto puesto) {
        puestoRepository.save(puesto);
    }

    public List<Puesto> getTodos() {
        return (List<Puesto>) puestoRepository.findAll();
    }
}
