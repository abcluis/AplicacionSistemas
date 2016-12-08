package com.itchihuahuaii.aplicacionsistemas.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
/**
 * Clase manejadora de la base de datos
 * @author Equipo Plataforma Mod
 * @version v0.7
 */

public class OperacionesBaseDatos {

    private static BaseDatosPlataforma baseDatos;


    private static OperacionesBaseDatos instancia = new OperacionesBaseDatos();
    /**
     * Constructor vacio
     */
    private OperacionesBaseDatos() {

    }

    /**
     * Metodo para cargar la base de datos en caso de que no exista
     * @param context contexto de la aplicacion
     * @return Devuelve la base de datos
     */
    public static OperacionesBaseDatos obtenerInstancia(Context context) {
        if (baseDatos == null) {
            baseDatos = new BaseDatosPlataforma(context);
        }
        return instancia;
    }

    /**
     * Metodo para obtener un cursor de la base de datos
     * @param query String con la instruccion SQL
     * @return Devuelve el cursor
     */
    public Cursor selectPlataformaDBCursor(String query) {
        // REGRESA NOMBRE DEL CURSO     NOMBRE DEL PROFESOR,    ID DEL CURSO
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        return db.rawQuery(query,null);
    }

    /**
     * Metodo para obtener un string de la base de datos
     * @param query String con la instruccion SQL
     * @return Devuelve el primer string del cursor obtenido
     */
    public String selectPlataformaDB(String query) {
        // REGRESA NOMBRE DEL CURSO     NOMBRE DEL PROFESOR,    ID DEL CURSO
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);
        if(c.moveToFirst()){
            return c.getString(0);
        }
        return "";

    }

    // --------------------------------INSERTS------------------------------------------------//

    /**
     * Metodo para insertar un nuevo usuario
     * @param nick nick del usuario
     * @param password password del usuario
     * @param tipo tipo del usuario
     */
    public void insertUsuario(String nick,String password,String tipo) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nick", nick);
        values.put("password", password);
        values.put("tipo", tipo);
        db.insertOrThrow("usuario", null, values);
    }

    /**
     * Metodo para insertar una nueva carrera
     * @param nombre nombre de la carrera
     */
    public void insertCarrera(String nombre) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        db.insertOrThrow("carrera", null, values);
    }

    /**
     * Metodo para insertar un nuevo profesor
     * @param nombre nombre del profesor
     * @param id_usuario id del usuario
     */
    public void insertProfesor(String nombre,int id_usuario) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("id_usuario", id_usuario);
        db.insertOrThrow("profesor", null, values);
    }

    /**
     * Metodo para insertar un nuevo alumno a un curso
     * @param id_alumno id del alumno
     * @param id_curso id del curso
     */
    public void insertAlumno_Curso(int id_alumno,int id_curso) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_alumno", id_alumno);
        values.put("id_curso", id_curso);
        db.insertOrThrow("alumno_curso", null, values);
    }

    /**
     * Metodo para insertar un nuevo curso
     * @param nombre nombre del curso
     * @param id_carrera id de la carrera
     * @param id_profesor id del profesor
     */
    public void insertCurso(String nombre,int id_carrera, int id_profesor) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("id_carrera", id_carrera);
        values.put("id_profesor", id_profesor);
        db.insertOrThrow("curso", null, values);
    }

    /**
     * Metodo para insertar un nuevo alumno
     * @param nombre nombre del alumno
     * @param id_usuario id del usuario
     */
    public void insertAlumno(String nombre,int id_usuario) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("id_usuario", id_usuario);
        db.insertOrThrow("alumno", null, values);
    }

    /**
     * Metodo para insertar nueva tarea
     * @param nombre de la tarea
     * @param id_curso id del curso
     * @param fecha fecha de entrega de la tarea
     */
    public void insertTarea(String nombre,int id_curso,String fecha) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("id_curso", id_curso);
        values.put("fecha", fecha);
        db.insertOrThrow("tarea", null, values);
    }
// -------------------------------UPDATES(Proceso) -------------------------------------//
/*
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
*/

    /**
     * Metodo para eliminar un columna
     * @param id String con el id de la fila a eliminar
     * @return Devuelve la cantidad de filas afectadas
     */
    public boolean deleteData(String id) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = "id" + "=?";
        String[] whereArgs = {id};

        int resultado = db.delete("tarea", whereClause, whereArgs);
        return resultado > 0;
    }

    /**
     * Metodo para obtener la base de datos
     * @return Devuelve la base de datos
     */

    public SQLiteDatabase getDb() {
        return baseDatos.getWritableDatabase();
    }


}
