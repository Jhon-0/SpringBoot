package com.inserta.crudalumnos.modelo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alumnos")

public class Alumno {

    @Id
    @Column(length = 9, columnDefinition = "CHAR(9)")
    private String nif;      // SQL -> nif CHAR(9) NOT NULL PRIMARY KEY
    

    @Column(length = 59, nullable = false)
    private String nombre;  // SQL -> Nombre VARCHAR(59)


    private Integer edad;    

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean genero;

    // Se añade las relaciones en la tabla principal
    // En el mappedBy se pone el nombre de la clase en minúsculas
    @OneToMany(mappedBy = "alumno")
    @JsonIgnore
    private List<AlumnoAsignatura> alumnoAsignaturas = new ArrayList<>();

}