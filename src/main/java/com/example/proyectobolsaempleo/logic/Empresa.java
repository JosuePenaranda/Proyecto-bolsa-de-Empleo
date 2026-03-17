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
@Table(name = "empresa")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "nombre", length = 100)
    private String nombre;

    @Size(max = 100)
    @Column(name = "localizacion", length = 100)
    private String localizacion;

    @Size(max = 100)
    @Column(name = "correo", length = 100)
    private String correo;

    @Size(max = 20)
    @Column(name = "telefono", length = 20)
    private String telefono;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Size(max = 100)
    @Column(name = "clave", length = 100)
    private String clave;

    @Column(name = "autorizado")
    private Boolean autorizado;

    @OneToMany(mappedBy = "idEmpresa")
    private Set<Puesto> puestos = new LinkedHashSet<>();

}