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
@Table(name = "puesto")
public class Puesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "salario")
    private Double salario;

    @Size(max = 10)
    @Column(name = "tipo_publicacion", length = 10)
    private String tipoPublicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa")
    private Empresa idEmpresa;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "idPuesto")
    private Set<PuestoRequisito> puestoRequisitos = new LinkedHashSet<>();

}