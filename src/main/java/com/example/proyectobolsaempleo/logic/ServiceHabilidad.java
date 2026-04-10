package com.example.proyectobolsaempleo.logic;

import com.example.proyectobolsaempleo.data.HabilidadRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service("serviceHabilidad")
public class ServiceHabilidad {

    @Autowired
    private HabilidadRepository habilidadRepository;

    public void guardar(Habilidad habilidad) {
        habilidadRepository.save(habilidad);
    }

    public List<Habilidad> listarPorOferente(Oferente oferente) {
        return (List<Habilidad>) habilidadRepository.findByIdOferente(oferente);
    }

    public boolean yaExiste(Oferente oferente, Caracteristica caracteristica) {
        return habilidadRepository.findByIdOferenteAndIdCaracteristica(oferente, caracteristica) != null;
    }


    public void actualizar(Integer id, Integer nuevoNivel) {
        Habilidad h = habilidadRepository.findById(id).orElse(null);
        if (h != null) {
            h.setNivel(nuevoNivel);
            habilidadRepository.save(h);
        }
    }
}
