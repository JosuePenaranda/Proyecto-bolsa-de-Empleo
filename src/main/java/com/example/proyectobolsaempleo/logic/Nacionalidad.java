package com.example.proyectobolsaempleo.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Nacionalidad {

    private String iso;
    private String nombre;
    private String descripcion;
    private String iso3;
    private Integer codigoNumero;
    private Integer codigoTelefono;
}