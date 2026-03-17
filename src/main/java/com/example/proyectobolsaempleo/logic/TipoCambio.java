package com.example.proyectobolsaempleo.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TipoCambio {

    private double venta;
    private double compra;
    private String moneda;
}