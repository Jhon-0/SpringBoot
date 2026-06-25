package com.inserta.crudalumnos.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inserta.crudalumnos.modelo.Alumno;
import com.inserta.crudalumnos.repositorio.AlumnoRepository;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List; // listas

// Para paginación
import org.springframework.data.domain.Page;
// Para paginación
import org.springframework.data.domain.PageRequest;
// Para ordenación
import org.springframework.data.domain.Sort;
// Para devolver un ResponseEntity
import org.springframework.http.ResponseEntity;

// Para recibir parámetros desde la URL
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

// Para la APIRest en el controlador se pone la anotación -> @RestController
@RestController
@RequestMapping("/api/alumnos") // <- Endpoint // @RequestMapping define el endpoint (la url que sale en
                                // swagger/postman)

public class AlumnoController {

    // Agregamos al repo
    private final AlumnoRepository alumnoRepo;

    // Constructor del repo
    public AlumnoController(AlumnoRepository alumnoRepo) {
        this.alumnoRepo = alumnoRepo;
    }

    // Por cada endpoint se crea un método
    // endpoint -> http://localhost:8080/api/alumnos/consultar
    @GetMapping("/consultar")
    // extra
    @Operation(summary = "Lista alumnos")
    public List<Alumno> verTodosAlumnos() {
        return alumnoRepo.findAll();
    }

    // Añadimos Swagger CTRL + MAY + P
    // Spring Initializr: Add Starter > SpringDoc Open Api Web
    // Se añade el paquete [Proceed] y se añade al Path
    // En application.properties se añade la url.path de swagger
    // http://localhost:8080/swagger-ui/index.html

    // GET por parámetro PK (findById) -> Parámetros van con { }
    // endpoint -> http://localhost:8080/api/alumnos/consultar/22B
    @GetMapping("/consultar/{nif}")
    @Operation(summary = "Ver alumno por NIF")
    public ResponseEntity<Alumno> verAlumnoPorNIF(@PathVariable String nif) {
        return alumnoRepo.findById(nif)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET consultar ordenado
    // endpoint -> http://localhost:8080/api/alumnos/consultar/ordenado?campo=edad
    @GetMapping("/consultar/ordenado")
    // extra
    @Operation(summary = "Consultar alumnos ordenado por nombre")
    // @RequestParam: recibe datos desde la URL (query params)
    public List<Alumno> verAlumnosOrdenados(@RequestParam(defaultValue = "nombre") String campo) {
        return alumnoRepo.findAll(Sort.by(campo).ascending());
    }

    // GET Alumos consultar pagina
    // endpoint -> http://localhost:8080/api/alumnos/consultar/pagina?pagina=0&tam=5

    @GetMapping("/consultar/pagina")
    // extra
    @Operation(summary = "Consultar alumnos paginado intervalo")
    // @RequestParam: recibe datos desde la URL (query params)
    // Page: devuelve resultados paginados con info extra (total, páginas, etc.)
    public Page<Alumno> verAlumnosPaginado(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tam) {
        return alumnoRepo.findAll(PageRequest.of(pagina, tam));
    }

    // GET consultar si exixte
    // endpoint -> http://localhost:8080/api/alumnos/consultar/ordenado?campo=edad
    @GetMapping("/existe{nif}")

    // extra
    @Operation(summary = "¿Existe un alumno por su DNI?")
    // @RequestParam: recibe datos desde la URL (query params)
    public boolean verAlumnosDni(@PathVariable String nif) {
        return alumnoRepo.existsById(nif);

    }

    // GET - R06 -> count() -> Número total de alumnos
    @GetMapping("/contar")
    @Operation(summary = "Contar el total de alumnos")
    public long contarTodosAlumnos() {
        return alumnoRepo.count();
    }

    // GET R07 → /api/alumnos/consultar/nombre
    // Paso1: Al no ser método estandar, se añade al repo
    // Paso2: Se agrega el nuevo método del repo aquí en el controlador
    @GetMapping("/consultar/nombre")
    @Operation(summary = "Buscar alumnos que contengan parte de 'nombre'")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    // GET R08 → /api/alumnos/consultar/genero/mujeres
    @GetMapping("/consultar/genero/mujeres")
    @Operation(summary = "Sacar lista Alumnas")
    public List<Alumno> verAlumnas() {
        return alumnoRepo.findByGenero(false);
    }

    // GET R09 → /api/alumnos/consultar/genero/hombres
    @GetMapping("/consultar/genero/hombres")
    @Operation(summary = "Sacar lista Alumnos")
    public List<Alumno> verAlumnos() {
        return alumnoRepo.findByGenero(true);
    }

    // GET R10 → /api/alumnos/consultar/contar/mujeres
    @GetMapping("/consultar/contar/mujeres")
    @Operation(summary = "Contar Alumnas")
    public String contarAlumnas() {
        return alumnoRepo.countByGenero(false) + " Alumnas";
    }

    // GET R11 → /api/alumnos/consultar/contar/hombres
    @GetMapping("/consultar/contar/hombres")
    @Operation(summary = "Contar Alumnos")
    public String contarAlumnos() {
        return alumnoRepo.countByGenero(true) + " Alumnos";
    }

    // POST - C01 -> /api/alumnos/crear/{nif}/{nombre}/{edad}/{genero}
    //
    // Hemos añadido un usuario como ejemplo:
    // http://localhost:8080/api/alumnos/crear/77D/Antonio%20Barrios/50/true
    // El %20 es un espacio en blanco, ya que no se puede poner un espacio en blanco
    // en la URL
    // Se puede poner un espacio en blanco con el signo +, pero no es recomendable
    @PostMapping("/crear/{nif}/{nombre}/{edad}/{genero}")
    @Operation(summary = "Inserta alumnos por parámetros en la URL")
    public Alumno crearAlumnoParametro(
            // Consejo del profe: en los @PathVariable usar siempre el objeto de la clase,
            // en este caso String, Integer y Boolean.
            // No usar primitivos
            @PathVariable String nif,
            @PathVariable String nombre,
            @PathVariable Integer edad,
            @PathVariable Boolean genero) {
        // Alumno alumno = new Alumno(nif, nombre, edad, genero, null);
        Alumno alumno = new Alumno();
        alumno.setNif(nif);
        alumno.setNombre(nombre);
        alumno.setEdad(edad);
        alumno.setGenero(genero);

        return alumnoRepo.save(alumno);
    }

    // POST - C02 -> /api/alumnos/crear
    @PostMapping("/crear")
    @Operation(summary = "Inserta alumnos por JSON en el body")
    public Alumno crearAlumnoJSON(@RequestParam Alumno alumno) {
        return alumnoRepo.save(alumno);
    }

    // POST - C03 -> saveAll(Iterable) -> Insertar VARIOS alumnos de golpe (JSON
    // array)
    // NOTA: El POST - C02 lo pongo después
    /*
     * Metemos varios alumnos en un JSON tipo array
     * 
     * [
     * { "nif": "11A", "nombre": "Ana",
     * "edad": 22, "genero": false },
     * { "nif": "22B", "nombre": "Bruno", "edad": 31, "genero": true
     * },
     * { "nif": "33C", "nombre": "Clara", "edad": 27, "genero": false }
     * ]
     */

    //
    // saveAll(Iterable) -> Insertar VARIOS alumnos de golpe (JSON array)

    @PostMapping("/crear/lote")
    @Operation(summary = "Crear varios alumnos a la vez con un array JSON")
    public List<Alumno> crearAlumnosEnLote(@RequestBody List<Alumno> alumnos) {
        return alumnoRepo.saveAll(alumnos);
    }




    // PUT - U01 -> Actualizar un alumno existente POR PARAMETROS EN LA URL
    // http://localhost:8080/api/alumnos/actualizar/22B/30
    @PutMapping("/actualizar/{nif}/{edad}/{genero}/{nombre}")
    @Operation(summary = "Actualizar un alumno por NIF en la URL")
    public Alumno actualizarAlumnoNif(

            @PathVariable String nif, @RequestBody Alumno alumnoJSON) {
        // Busco el alumno para actualizar
        Alumno alumno = alumnoRepo.findById(nif)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        if (alumnoJSON.getNombre() != null) {
            alumno.setNombre(alumnoJSON.getNombre());
        }
        if (alumnoJSON.getEdad() != null) {
            alumno.setEdad(alumnoJSON.getEdad());
        }
        if (alumnoJSON.getGenero() != null) {
            alumno.setGenero(alumnoJSON.getGenero());
        }


        return alumnoRepo.save(alumno);

    }





      // PUT - U02 -> Actualizar un alumno existente POR jSON EN EL BODY
    // http://localhost:8080/api/alumnos/actualizar/22B/30
    // {
    // "nif": "22B",
    // "nombre": "Bruno",
    // "edad": 30,
    // "genero": true
    // }
    @PutMapping("/actualizar/{nif}/{edad}/{genero}/{nombre}")
    @Operation(summary = "Actualizar un alumno por JSON en el body")
    public Alumno actualizarAlumnoJson( @RequestBody Alumno alumnoJSON) {
        // Busco el alumno para actualizar
        Alumno alumno = alumnoRepo.findById(alumnoJSON.getNif()) // hemos cambiado el nif por alumnoJSON.getNif() para que busque el alumno por el nif que viene en el JSON
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        if (alumnoJSON.getNombre() != null) {
            alumno.setNombre(alumnoJSON.getNombre());
        }
        if (alumnoJSON.getEdad() != null) {
            alumno.setEdad(alumnoJSON.getEdad());
        }
        if (alumnoJSON.getGenero() != null) {
            alumno.setGenero(alumnoJSON.getGenero());
        }


        return alumnoRepo.save(alumno);

    }


    //PUT - U03 -> Actualizar un alumno con todos los parametros
    // http://localhost:8080/api/alumnos/actualizar/22B/30
    @PutMapping("/actualizar/{nif}/{edad}/{genero}/{nombre}")
    @Operation(summary = "Actualizar un alumno con todos los parámetros")
    public Alumno actualizarAlumnoFull(
            @PathVariable String nif,
            @PathVariable Integer edad,
            @PathVariable Boolean genero,
            @PathVariable String nombre) {

        Alumno alumno = alumnoRepo.findById(nif)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        alumno.setNombre(nombre);
        alumno.setEdad(edad);
        alumno.setGenero(genero);

        return alumnoRepo.save(alumno);
    }















    // llave de cierre NO TOCAR!""

}