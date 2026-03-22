package com.example.proyectobolsaempleo.data;

import com.example.proyectobolsaempleo.logic.Caracteristica;
import com.example.proyectobolsaempleo.logic.Habilidad;
import com.example.proyectobolsaempleo.logic.Oferente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HabilidadRepository extends CrudRepository<Habilidad, Integer> {
    List<Habilidad> findByIdOferente(Oferente idOferente);

    Habilidad findByIdOferenteAndIdCaracteristica(Oferente idOferente, Caracteristica idCaracteristica);

}
