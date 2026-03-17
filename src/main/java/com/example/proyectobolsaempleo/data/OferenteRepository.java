package com.example.proyectobolsaempleo.data;

import com.example.proyectobolsaempleo.logic.Oferente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OferenteRepository extends CrudRepository<Oferente, String> {
}
