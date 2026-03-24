package com.example.proyectobolsaempleo.logic;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServiceBusqueda {

    public double calcularSimilitudCoseno(List<Integer> oferente, List<Integer> puesto) {
        double numerador = 0;
        double sumaOferente = 0;
        double sumaPuesto = 0;

        for (int i = 0; i < oferente.size(); i++) {
            double q = oferente.get(i);
            double d = puesto.get(i);

            numerador += q * d;
            sumaOferente += q * q;
            sumaPuesto += d * d;
        }

        double denominador = Math.sqrt(sumaOferente) * Math.sqrt(sumaPuesto);

        if (denominador == 0) return 0;

        return numerador / denominador;
    }
}
