package com.itchihuahuaii.aplicacionsistemas.sqlite;

import java.util.UUID;
/**
 * Created by usuario1 on 11/3/2016.
 */

public class ContratoPlataforma {

    interface ColumnasProfesor {
        String ID = "id";
        String NOMBRE = "nombre";
    }
    interface ColumnasAlumno {
        String ID = "id";
        String NOMBRE = "nombre";
    }
    interface ColumnasAlumnoCurso {
        String ID = "id";
        String ID_ALUMNO = "id_alumno";
        String ID_CURSO = "id_curso";
    }
    interface ColumnasCurso {
        String ID = "id";
        String NOMBRE = "nombre";
        String ID_CARRERA = "id_carrera";
        String ID_PROFESOR = "id_profesor";
    }
    interface ColumnasCarrera {
        String ID = "id";
        String NOMBRE = "nombre";
    }

    interface ColumnasTarea {
        String ID = "id";
        String NOMBRE = "nombre";
        String ID_CURSO = "id_curso";
    }

    private ContratoPlataforma() {
    }

    public static class Alumno implements ColumnasAlumno {
        public static String generarIdAlumno() {
            return "ALU-" + UUID.randomUUID().toString();
        }
    }

    public static class Profesor implements ColumnasProfesor {
        public static String generarIdProfesor() {
            return "PRO-" + UUID.randomUUID().toString();
        }
    }

    public static class Carrera implements ColumnasCarrera {
        public static String generarIdCarrera() {
            return "CAR-" + UUID.randomUUID().toString();
        }
    }

    public static class Curso implements ColumnasCurso {
        public static String generarIdCurso() {
            return "CUR-" + UUID.randomUUID().toString();
        }
    }

    public static class AlumnoCurso implements ColumnasAlumnoCurso {
        public static String generarIdAlumnoCurso() {
            return "ALUCUR-" + UUID.randomUUID().toString();
        }
    }

    public static class Tarea implements ColumnasTarea {
        public static String generarIdTareas() {
            return "TAR-" + UUID.randomUUID().toString();
        }
    }
}


