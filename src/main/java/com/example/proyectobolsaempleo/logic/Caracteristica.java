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
@Table(name = "caracteristica")
public class Caracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "nombre", length = 100)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_padre")
    private Caracteristica idPadre;

    @OneToMany(mappedBy = "idPadre")
    private Set<Caracteristica> caracteristicas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idCaracteristica")
    private Set<Habilidad> habilidads = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idCaracteristica")
    private Set<PuestoRequisito> puestoRequisitos = new LinkedHashSet<>();

}