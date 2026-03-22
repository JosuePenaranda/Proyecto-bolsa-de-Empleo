package com.example.proyectobolsaempleo.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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
    private ServiceLogin serviceLogin;

    @Autowired
    private ServicePuesto servicePuesto;

    @Autowired
    private ServiceReporte serviceReporte;

    @Autowired
    private ServiceHabilidad serviceHabilidad;

    // Getters
    public ServiceAdministrador getServiceAdministrador() { return serviceAdministrador; }
    public ServiceCaracteristica getServiceCaracteristica() { return serviceCaracteristica; }
    public ServiceEmpresa getServiceEmpresa() { return serviceEmpresa; }
    public ServiceOferente getServiceOferente() { return serviceOferente; }
    public ServiceLogin getServiceLogin() { return serviceLogin; }
    public ServicePuesto getServicePuesto() { return servicePuesto; }
    public ServiceReporte getServiceReporte() { return serviceReporte; }
    public ServiceHabilidad getServiceHabilidad() { return serviceHabilidad; }

}