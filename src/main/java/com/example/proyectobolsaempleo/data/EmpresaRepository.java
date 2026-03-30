package com.example.proyectobolsaempleo.data;

import com.example.proyectobolsaempleo.logic.Empresa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpresaRepository extends CrudRepository<Empresa, String> {

    Empresa findByCorreo(String correo);

    List<Empresa> findByAutorizado(Boolean autorizado);

    boolean existsByCorreo(String correo);

    boolean existsByTelefono(String telefono);

    boolean existsByNombre(String nombre);
}
