package com.example.proyectobolsaempleo.data;

import com.example.proyectobolsaempleo.logic.Administrador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Administrador, String> {

    Administrador findByCorreoAndClave(String correo, String clave);
}
