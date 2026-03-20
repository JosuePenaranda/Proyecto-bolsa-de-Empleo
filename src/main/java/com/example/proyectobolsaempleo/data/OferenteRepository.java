package com.example.proyectobolsaempleo.data;

import com.example.proyectobolsaempleo.logic.Oferente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OferenteRepository extends CrudRepository<Oferente, String> {

    Oferente findByCorreo(String correo);

    List<Oferente> findByAutorizado(Boolean autorizado);

}
