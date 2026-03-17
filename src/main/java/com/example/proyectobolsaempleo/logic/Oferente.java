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
    @Column(name = "identificacion", nullable = false, length = 50)
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

    @Column(name = "telefono")
    private Integer telefono;

    @Size(max = 100)
    @Column(name = "correo", length = 100)
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

}