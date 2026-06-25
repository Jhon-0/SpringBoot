package com.inserta.crudalumnos.carga;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.inserta.crudalumnos.modelo.Alumno;
import com.inserta.crudalumnos.modelo.AlumnoAsignatura;
import com.inserta.crudalumnos.modelo.Asignatura;
import com.inserta.crudalumnos.repositorio.AlumnoAsignaturaRepository;
import com.inserta.crudalumnos.repositorio.AlumnoRepository;
import com.inserta.crudalumnos.repositorio.AsignaturaRepository;


@Component
public class DatosIniciales implements CommandLineRunner {


    // añadimos repositorio
    private final AlumnoRepository alumnoRepo;
    private final AsignaturaRepository asignaturaRepo;
    private final AlumnoAsignaturaRepository alumnoAsignaturaRepo;


    // CReamos el contructor completo
    public DatosIniciales(AlumnoRepository alumnoRepo, AsignaturaRepository asignaturaRepo,
            AlumnoAsignaturaRepository alumnoAsignaturaRepo) {
        this.alumnoRepo = alumnoRepo;
        this.asignaturaRepo = asignaturaRepo;
        this.alumnoAsignaturaRepo = alumnoAsignaturaRepo;
    }

    
    // Metemos los datos en el "run"
    @Override
    public void run(String... args) throws Exception {
        //comprobamos que las tablas estan vacias
        if (alumnoRepo.count()== 0 && asignaturaRepo.count() == 0) {
            //crear asignatura
            Asignatura bbdd = new Asignatura(
                null, 
                "Bases de datos", 
                1, 
                List.of(1.1f, 2.2f),null);

            Asignatura prog = new Asignatura(
                null, 
                "Programacion", 
                1, 
                List.of(2.2f, 2.3f),new ArrayList<>());
            
            Asignatura fol = new Asignatura(
                null, 
                "Form. orientacion Lab", 
                1, 
                List.of(2.2f, 2.3f),new ArrayList<>());

            asignaturaRepo.saveAll(List.of(bbdd, prog, fol));

            //
            Alumno parraga  =   new Alumno("11A", "Parraga", 23, true, new ArrayList<>());
            Alumno maria    =   new Alumno("22B", "Maria", 21, false, new ArrayList<>());
            Alumno juan     =   new Alumno("33C", "Juan", 24, true, new ArrayList<>());
            Alumno lucia    =   new Alumno("44D", "Lucia", 22, false, new ArrayList<>());
            Alumno carlos   =   new Alumno("55E", "Carlos", 25, true, new ArrayList<>());
            
            alumnoRepo.saveAll(List.of(parraga, maria, juan, lucia, carlos));

            List<AlumnoAsignatura> relacion = List.of();
            new AlumnoAsignatura(null, parraga, fol);
            new AlumnoAsignatura(null, parraga, bbdd);

            new AlumnoAsignatura(null, maria, bbdd);
            new AlumnoAsignatura(null, juan, prog);
            new AlumnoAsignatura(null, juan, bbdd);
            new AlumnoAsignatura(null, juan, fol);

            new AlumnoAsignatura(null, lucia, fol);
            new AlumnoAsignatura(null, lucia, prog);

            new AlumnoAsignatura(null, carlos, prog);


            alumnoAsignaturaRepo.saveAll(relacion);
        }
      
        
    }





}
