package com.example.proyectobolsaempleo.data;

import com.example.proyectobolsaempleo.logic.Caracteristica;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CaracteristicaRepository extends CrudRepository<Caracteristica, Integer> {
    List<Caracteristica> findByIdPadreIsNull();
    List<Caracteristica> findByIdPadre(Caracteristica padre);
}