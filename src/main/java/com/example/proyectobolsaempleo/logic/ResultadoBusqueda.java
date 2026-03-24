package com.example.proyectobolsaempleo.logic;

public class ResultadoBusqueda {

    private Oferente oferente;
    private double porcentaje;

    public ResultadoBusqueda(Oferente oferente, double porcentaje) {
        this.oferente = oferente;
        this.porcentaje = porcentaje;
    }

    public Oferente getOferente() {
        return oferente;
    }

    public double getPorcentaje() {
        return porcentaje;
    }
}
