package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.data.CaracteristicaRepository;
import com.example.proyectobolsaempleo.data.PuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service("servicePuesto")
public class ServicePuesto {

    @Autowired
    private PuestoRepository puestoRepository;

    @Autowired
    private CaracteristicaRepository caracteristicaRepository;

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

    public void guardarPuestoConRequisitos(Puesto puesto,
                                           List<Integer> ids,
                                           List<Integer> niveles) {

        for (int i = 0; i < ids.size(); i++) {

            Caracteristica c = caracteristicaRepository
                    .findById(ids.get(i))
                    .orElse(null);

            if (c != null) {

                PuestoRequisito pr = new PuestoRequisito();
                pr.setIdPuesto(puesto);
                pr.setIdCaracteristica(c);
                pr.setNivel(niveles.get(i));

                puesto.getPuestoRequisitos().add(pr);
            }
        }

        puesto.setActivo(true);

        puestoRepository.save(puesto);
    }

    public List<Puesto> getTodos() {
        return (List<Puesto>) puestoRepository.findAll();
    }

    public List<Puesto> getTop5Publicos() {
        return puestoRepository.findTop5Publicos();
    }

    public List<Puesto> buscarPorCaracteristicas(List<Integer> idsSeleccionados) {
        if (idsSeleccionados == null || idsSeleccionados.isEmpty()) {
            return getTop5Publicos();
        }

        // Expandir padres — si seleccionó un padre incluir sus hijos
        List<Integer> idsExpandidos = new ArrayList<>();
        for (Integer id : idsSeleccionados) {
            Caracteristica c = caracteristicaRepository.findById(id).orElse(null);
            if (c != null) {
                List<Caracteristica> hijos = caracteristicaRepository.findByIdPadre(c);
                if (hijos.isEmpty()) {
                    // Es hoja, agregar directo
                    idsExpandidos.add(id);
                } else {
                    // Es padre, agregar todos sus hijos
                    hijos.forEach(h -> idsExpandidos.add(h.getId()));
                }
            }
        }

        return puestoRepository.findByCaracteristicas(idsExpandidos);
    }

    public List<Puesto> getPuestosPorEmpresa(Empresa empresa) {
        return puestoRepository.findByIdEmpresa(empresa);
    }

    public void desactivarPuesto(Integer id) {
        Puesto puesto = puestoRepository.findById(id).orElse(null);
        if (puesto != null) {
            puesto.setActivo(false);
            puestoRepository.save(puesto);
        }
    }

    public void activarPuesto(Integer id) {
        Puesto puesto = puestoRepository.findById(id).orElse(null);
        if (puesto != null) {
            puesto.setActivo(true);
            puestoRepository.save(puesto);
        }
    }


}
