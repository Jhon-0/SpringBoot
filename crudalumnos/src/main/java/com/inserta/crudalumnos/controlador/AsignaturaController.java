 package com.inserta.crudalumnos.controlador;

 import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inserta.crudalumnos.modelo.Alumno;
import com.inserta.crudalumnos.modelo.Asignatura;
import com.inserta.crudalumnos.repositorio.AlumnoRepository;
import com.inserta.crudalumnos.repositorio.AsignaturaRepository;
import com.inserta.crudalumnos.repositorio.AsignaturaRepository;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


// Para la APIRest en el controlador se pone la anotación -> @RestController
@RestController
@RequestMapping("/api/asignatura") // <- Endpoint // @RequestMapping define el endpoint (la url que sale en
                                // swagger/postman)

public class AsignaturaController {
     // Agregamos al repo
    private final AsignaturaRepository asinaturaRepo;


    // Constructor del repo
    public AsignaturaController(AsignaturaRepository asinaturaRepo) {
        this.asinaturaRepo = asinaturaRepo;
    }

    // Por cada endpoint se crea un método
    // endpoint -> /api/alumnos/consultar
    @GetMapping("/consultar")
    // extra
    @Operation(summary = "Lista asignatura")
    public List<Asignatura> verAsignatura(){
        return asinaturaRepo.findAll();
    }

}
