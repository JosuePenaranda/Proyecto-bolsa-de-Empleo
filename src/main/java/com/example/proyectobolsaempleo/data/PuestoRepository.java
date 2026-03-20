package com.example.proyectobolsaempleo.data;

import com.example.proyectobolsaempleo.logic.Puesto;
import com.example.proyectobolsaempleo.logic.PuestoRequisito;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuestoRepository extends CrudRepository<Puesto, Integer> {

    // Trae los puestos del mes/año con la empresa ya cargada
    @Query("SELECT p FROM Puesto p LEFT JOIN FETCH p.idEmpresa WHERE " +
            "MONTH(p.fechaPublicacion) = :mes AND YEAR(p.fechaPublicacion) = :anio " +
            "ORDER BY p.fechaPublicacion ASC")
    List<Puesto> findByMesYAnio(@Param("mes") int mes, @Param("anio") int anio);

    // Trae los requisitos de un puesto con la característica ya cargada
    @Query("SELECT pr FROM PuestoRequisito pr LEFT JOIN FETCH pr.idCaracteristica " +
            "WHERE pr.idPuesto.id = :idPuesto")
    List<PuestoRequisito> findRequisitorByPuesto(@Param("idPuesto") Integer idPuesto);

}
