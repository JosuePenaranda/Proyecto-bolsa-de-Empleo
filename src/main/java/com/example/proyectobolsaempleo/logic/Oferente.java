package com.example.proyectobolsaempleo.logic;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "oferente")
public class Oferente {
    @Id
    @Size(max = 50)
    @Column(unique = true, name = "identificacion", nullable = false, length = 50)
    private String identificacion;

    @Size(max = 100)
    @Column(name = "nombre", length = 100)
    private String nombre;

    @Size(max = 100)
    @Column(name = "primer_apellido", length = 100)
    private String primerApellido;

    @Size(max = 50)
    @Column(name = "nacionalidad", length = 50)
    private String nacionalidad;

    @Column(unique = true, name = "telefono")
    private String telefono;

    @Size(max = 100)
    @Column(unique = true, name = "correo", length = 100)
    private String correo;

    @Size(max = 100)
    @Column(name = "lugar_residencia", length = 100)
    private String lugarResidencia;

    @Size(max = 100)
    @Column(name = "clave", length = 100)
    private String clave;

    @Column(name = "autorizado")
    private Boolean autorizado;

    @Lob
    @Column(name = "curriculum")
    private String curriculum;

    @OneToMany(mappedBy = "idOferente")
    private Set<Habilidad> habilidads = new LinkedHashSet<>();

    // Se acepta el formato del teléfono 12345678 o 1234-5678
    // Independientemente de cuál se ingrese, se añade con el formato 1234-5678

    public void setTelefono(String telefono) {
        if (telefono != null) {
            telefono = telefono.replaceAll("\\D", "");

            if (telefono.length() == 8) {
                this.telefono = telefono.substring(0,4) + "-" + telefono.substring(4);
                return;
            }
        }
        this.telefono = telefono;
    }

}