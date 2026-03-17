package com.example.proyectobolsaempleo.logic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "administrador")
public class Administrador {
    @Id
    @Size(max = 50)
    @Column(name = "identificacion", nullable = false, length = 50)
    private String identificacion;

    @Size(max = 100)
    @Column(name = "correo", length = 100)
    private String correo;

    @Size(max = 100)
    @Column(name = "clave", length = 100)
    private String clave;

}