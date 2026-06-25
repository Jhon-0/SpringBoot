package com.inserta.crudalumnos.modelo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "asignaturas")
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;      // SQL -> nif CHAR(9) NOT NULL PRIMARY KEY

    @Column(length = 59, nullable = false)
    private String denominacion;  // SQL -> Nombre VARCHAR(59)

    @Column(columnDefinition = "TINYINT")
    private int curso;

    // Vamos a añadir un campo ENUM
    // IMPORTANTE: No hace falta crear la tabla del ENUM (aulas)
    @ElementCollection
    @CollectionTable(name="asignaturas_aulas",
        joinColumns = @JoinColumn(name="asignatura_id")
    )
    private List<Float> aulas = new ArrayList<>();

    // Falta poner las relaciones con la tabla secundaria
    @OneToMany(mappedBy = "asignatura")
    @JsonIgnore
    private List<AlumnoAsignatura> alumnoAsignaturas = new ArrayList<>();


}