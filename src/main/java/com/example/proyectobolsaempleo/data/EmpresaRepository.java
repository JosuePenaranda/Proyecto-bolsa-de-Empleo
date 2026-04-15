package com.example.proyectobolsaempleo.data;

import com.example.proyectobolsaempleo.logic.Empresa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpresaRepository extends CrudRepository<Empresa, String> {

    Empresa findByCorreo(String correo);

    List<Empresa> findByAutorizado(Boolean autorizado);

    //Verificación para saber si ya fueron registrados

    boolean existsByCorreo(String correo);

    boolean existsByNombre(String nombre);
}
