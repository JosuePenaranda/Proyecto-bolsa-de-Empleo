package com.example.proyectobolsaempleo.modelo;
import com.example.proyectobolsaempleo.logic.*;

public class ModeloDatos {

    private static ModeloDatos _instancia = null;

    private ServiceDatos serviceDatos;

    // Se reciben los beans para el contexto de las clases

    private ModeloDatos(){
        serviceDatos = SpringContext.getBean(ServiceDatos.class);
    }

    public static ModeloDatos getInstancia(){
        if(_instancia == null){
            _instancia = new ModeloDatos();
        }
        return _instancia;
    }

    public ServiceDatos getServiceDatos() {
        return serviceDatos;
    }

}
