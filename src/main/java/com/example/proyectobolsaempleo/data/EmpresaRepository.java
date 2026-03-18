package com.example.proyectobolsaempleo.data;

import com.example.proyectobolsaempleo.logic.Empresa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends CrudRepository<Empresa, String> {

    Empresa findByCorreoAndClave(String correo, String clave);

}
