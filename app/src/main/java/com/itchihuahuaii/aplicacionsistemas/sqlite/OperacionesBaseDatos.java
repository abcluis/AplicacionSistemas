package com.itchihuahuaii.aplicacionsistemas.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Created by usuario1 on 11/3/2016.
 */

public class OperacionesBaseDatos {

    private static BaseDatosPlataforma baseDatos;


    private static OperacionesBaseDatos instancia = new OperacionesBaseDatos();

    private OperacionesBaseDatos() {

    }

    public static OperacionesBaseDatos obtenerInstancia(Context context) {
        if (baseDatos == null) {
            baseDatos = new BaseDatosPlataforma(context);
        }
        return instancia;
    }

    public Cursor obtenerCursosCarrera(String id) {
        // REGRESA NOMBRE DEL CURSO     NOMBRE DEL PROFESOR,    ID DEL CURSO
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        System.out.print(id);
        return db.rawQuery("SELECT curso.nombre,profesor.nombre,curso.id " +
                "FROM profesor,curso,carrera " +
                "WHERE curso.id_carrera=carrera.id AND curso.id_profesor=profesor.id AND carrera.id='"+id+"'",null);
    }

    public Cursor obtenerTabMisCursos(String id) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        System.out.print(id);
        return db.rawQuery("SELECT curso.nombre,profesor.nombre,curso.id " +
                "FROM curso,profesor,alumno_curso " +
                "WHERE curso.id_profesor=profesor.id AND alumno_curso.id_curso=curso.id AND alumno_curso.id_alumno='"+id+"'",null);
    }
    public Cursor obtenerMisTareas(String id) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        System.out.print(id);
        return db.rawQuery("SELECT tarea.nombre,curso.nombre,tarea.id " +
                "FROM tarea,curso,alumno_curso " +
                "WHERE alumno_curso.id_alumno='"+id+"' AND alumno_curso.id_curso=curso.id AND tarea.id_curso=curso.id",null);
    }
    public Cursor obtenerTareaDatos(String id) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        System.out.print(id);
        return db.rawQuery("SELECT tarea.nombre,curso.nombre " +
                "FROM tarea,curso " +
                "WHERE tarea.id='"+id+"' AND tarea.id_curso=curso.id ",null);
    }
    public Cursor obtenerCursoDatos(String id) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        return db.rawQuery("SELECT curso.nombre,tarea.nombre " +
                "FROM tarea,curso " +
                "WHERE curso.id='"+id+"' AND tarea.id_curso=curso.id ",null);
    }
    public Cursor obtenerTabMisCursosProfesor(String id) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        System.out.print(id);
        return db.rawQuery("SELECT curso.nombre , carrera.nombre, curso.id " +
                "FROM curso,profesor,carrera " +
                "WHERE curso.id_profesor=profesor.id AND curso.id_carrera=carrera.id AND profesor.id='"+id+"'",null);
    }
    public Cursor obtenerTabMisTareasProfesor(String id) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        System.out.print(id);
        return db.rawQuery("SELECT tarea.nombre,curso.nombre,tarea.id " +
                "FROM curso,profesor,tarea " +
                "WHERE curso.id_profesor=profesor.id AND tarea.id_curso=curso.id AND profesor.id='"+id+"'",null);
    }
    public Cursor obtenerParticipantesCurso(String id) {
        // El id corresponde al curso
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        System.out.print(id);
        return db.rawQuery("SELECT alumno.nombre,curso.nombre " +
                "FROM alumno,alumno_curso,curso " +
                "WHERE alumno_curso.id_alumno=alumno.id AND alumno_curso.id_curso=curso.id AND curso.id='"+id+"'",null);
    }



    // --------------------------------INSERTS------------------------------------------------//

    public String insertarProfesor(String nombre) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String id = ContratoPlataforma.Profesor.generarIdProfesor();
        ContentValues values = new ContentValues();
        values.put(ContratoPlataforma.Profesor.ID, id);
        values.put(ContratoPlataforma.Profesor.NOMBRE, nombre);
        db.insertOrThrow(BaseDatosPlataforma.TABLAS.PROFESOR, null, values);
        return id;
    }
    public String insertarAlumno(String nombre) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String id = ContratoPlataforma.Alumno.generarIdAlumno();
        ContentValues values = new ContentValues();
        values.put(ContratoPlataforma.Alumno.ID, id);
        values.put(ContratoPlataforma.Alumno.NOMBRE, nombre);
        db.insertOrThrow(BaseDatosPlataforma.TABLAS.ALUMNO, null, values);
        return id;
    }
    public String insertarAlumnoCurso(String idAlumno,String idCurso) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String id = ContratoPlataforma.AlumnoCurso.generarIdAlumnoCurso();
        ContentValues values = new ContentValues();
        values.put(ContratoPlataforma.AlumnoCurso.ID, id);
        values.put(ContratoPlataforma.AlumnoCurso.ID_ALUMNO, idAlumno);
        values.put(ContratoPlataforma.AlumnoCurso.ID_CURSO, idCurso);
        db.insertOrThrow(BaseDatosPlataforma.TABLAS.ALUMNO_CURSO, null, values);
        return id;
    }
    public String insertarCurso(String nombre,String idCarrera,String idProfesor) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String id = ContratoPlataforma.Curso.generarIdCurso();
        ContentValues values = new ContentValues();
        values.put(ContratoPlataforma.Curso.ID, id);
        values.put(ContratoPlataforma.Curso.NOMBRE, nombre);
        values.put(ContratoPlataforma.Curso.ID_CARRERA, idCarrera);
        values.put(ContratoPlataforma.Curso.ID_PROFESOR, idProfesor);
        db.insertOrThrow(BaseDatosPlataforma.TABLAS.CURSO, null, values);
        return id;
    }
    public String insertarCarrera(String nombre) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String id = ContratoPlataforma.Carrera.generarIdCarrera();
        ContentValues values = new ContentValues();
        values.put(ContratoPlataforma.Carrera.ID, id);
        values.put(ContratoPlataforma.Carrera.NOMBRE, nombre);
        db.insertOrThrow(BaseDatosPlataforma.TABLAS.CARRERA, null, values);
        return id;
    }
    public String insertarTarea(String nombre,String idCurso) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String id = ContratoPlataforma.Tarea.generarIdTareas();
        ContentValues values = new ContentValues();
        values.put(ContratoPlataforma.Tarea.ID, id);
        values.put(ContratoPlataforma.Tarea.NOMBRE, nombre);
        values.put(ContratoPlataforma.Tarea.ID_CURSO, idCurso);
        db.insertOrThrow(BaseDatosPlataforma.TABLAS.TAREA, null, values);
        return id;
    }
// -------------------------------UPDATES -------------------------------------//

    public boolean updateTarea(String id,String nombre,String idCurso) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContratoPlataforma.Tarea.NOMBRE, nombre);
        values.put(ContratoPlataforma.Tarea.ID_CURSO, idCurso);
        String whereClause = String.format("%s=?", ContratoPlataforma.Tarea.ID);
        String[] whereArgs = {id};

        int resultado = db.update(BaseDatosPlataforma.TABLAS.TAREA, values, whereClause, whereArgs);

        return resultado > 0;
    }

    public boolean eliminarTarea(String id) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoPlataforma.Tarea.ID + "=?";
        String[] whereArgs = {id};

        int resultado = db.delete(BaseDatosPlataforma.TABLAS.TAREA, whereClause, whereArgs);
        return resultado > 0;
    }

    // ---------------------------- OBTENER TODOS LOS DATOS --------------------
    public Cursor obtenerCarrera() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", BaseDatosPlataforma.TABLAS.CARRERA);

        return db.rawQuery(sql, null);
    }

    public Cursor obtenerAlumno() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", BaseDatosPlataforma.TABLAS.ALUMNO);

        return db.rawQuery(sql, null);
    }

    public Cursor obtenerCurso() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", BaseDatosPlataforma.TABLAS.CURSO);

        return db.rawQuery(sql, null);
    }

    public Cursor obtenerAlumnoCurso() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", BaseDatosPlataforma.TABLAS.ALUMNO_CURSO);

        return db.rawQuery(sql, null);
    }
    public Cursor obtenerProfesor() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", BaseDatosPlataforma.TABLAS.PROFESOR);

        return db.rawQuery(sql, null);
    }
    public Cursor obtenerTarea() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", BaseDatosPlataforma.TABLAS.TAREA);

        return db.rawQuery(sql, null);
    }

    public SQLiteDatabase getDb() {
        return baseDatos.getWritableDatabase();
    }


}
