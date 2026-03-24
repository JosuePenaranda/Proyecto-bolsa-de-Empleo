package com.example.proyectobolsaempleo.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceDatos {

    @Autowired
    private ServiceAdministrador serviceAdministrador;

    @Autowired
    private ServiceCaracteristica serviceCaracteristica;

    @Autowired
    private ServiceEmpresa serviceEmpresa;

    @Autowired
    private ServiceOferente serviceOferente;

    @Autowired
    private ServiceReporte serviceReporte;

    @Autowired
    private ServicePuesto servicePuesto;

    @Autowired
    private ServiceHabilidad serviceHabilidad;

    @Autowired
    private ServiceLogin serviceLogin;

    @Autowired
    private ServiceBusqueda serviceBusqueda;

    public ServiceAdministrador getServiceAdministrador() {
        return serviceAdministrador;
    }

    public ServiceCaracteristica getServiceCaracteristica() {
        return serviceCaracteristica;
    }

    public ServiceEmpresa getServiceEmpresa() {
        return serviceEmpresa;
    }

    public ServiceOferente getServiceOferente() {
        return serviceOferente;
    }

    public ServiceReporte getServiceReporte() {
        return serviceReporte;
    }

    public ServicePuesto getServicePuesto() {
        return servicePuesto;
    }

    public ServiceHabilidad getServiceHabilidad() {
        return serviceHabilidad;
    }

    public ServiceLogin getServiceLogin() {
        return serviceLogin;
    }

    public ServiceBusqueda getServiceBusqueda() {
        return serviceBusqueda;
    }
}