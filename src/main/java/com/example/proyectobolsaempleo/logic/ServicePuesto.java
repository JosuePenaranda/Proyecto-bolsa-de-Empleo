package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.data.CaracteristicaRepository;
import com.example.proyectobolsaempleo.data.PuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service("servicePuesto")
public class ServicePuesto {

    @Autowired
    private PuestoRepository puestoRepository;

    @Autowired
    private CaracteristicaRepository caracteristicaRepository;

    @Autowired
    private ServiceBusqueda serviceBusqueda;

    @Autowired
    private ServiceOferente serviceOferente;

    @Autowired
    private ServiceCaracteristica serviceCaracteristica;

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

    private List<Integer> construirVectorOferente(Oferente o, List<Caracteristica> todas) {
        List<Integer> vector = new ArrayList<>();

        for (Caracteristica c : todas) {
            int nivel = 0;

            for (Habilidad h : o.getHabilidads()) {
                if (h.getIdCaracteristica().getId().equals(c.getId())) {
                    nivel = h.getNivel() != null ? h.getNivel() : 0;
                    break;
                }
            }

            vector.add(nivel);
        }

        return vector;
    }

    private List<Integer> construirVectorPuesto(Puesto p, List<Caracteristica> todas) {
        List<Integer> vector = new ArrayList<>();

        for (Caracteristica c : todas) {
            int nivel = 0;

            for (PuestoRequisito r : p.getPuestoRequisitos()) {
                if (r.getIdCaracteristica().getId().equals(c.getId())) {
                    nivel = r.getNivel() != null ? r.getNivel() : 0;
                    break;
                }
            }

            vector.add(nivel);
        }

        return vector;
    }

    @Transactional
    public List<ResultadoBusqueda> buscarCandidatos(Puesto puesto) {

        List<Oferente> oferentes = serviceOferente.listar();
        List<Caracteristica> todas = serviceCaracteristica.getHojas();

        List<Integer> vectorPuesto = construirVectorPuesto(puesto, todas);

        List<ResultadoBusqueda> resultados = new ArrayList<>();

        for (Oferente o : oferentes) {

            List<Integer> vectorOferente = construirVectorOferente(o, todas);

            double sim = serviceBusqueda.calcularSimilitudCoseno(vectorOferente, vectorPuesto);

            resultados.add(new ResultadoBusqueda(o, sim * 100));
        }

        resultados.sort((a, b) -> Double.compare(b.getPorcentaje(), a.getPorcentaje()));

        return resultados;
    }


}
