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

    public Cursor selectPlataformaDBCursor(String query) {
        // REGRESA NOMBRE DEL CURSO     NOMBRE DEL PROFESOR,    ID DEL CURSO
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        return db.rawQuery(query,null);
    }
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

    public void insertUsuario(String nick,String password,String tipo) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nick", nick);
        values.put("password", password);
        values.put("tipo", tipo);
        db.insertOrThrow("usuario", null, values);
    }
    public void insertCarrera(String nombre) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        db.insertOrThrow("carrera", null, values);
    }
    public void insertProfesor(String nombre,int id_usuario) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("id_usuario", id_usuario);
        db.insertOrThrow("profesor", null, values);
    }
    public void insertAlumno_Curso(int id_alumno,int id_curso) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_alumno", id_alumno);
        values.put("id_curso", id_curso);
        db.insertOrThrow("alumno_curso", null, values);
    }
    public void insertCurso(String nombre,int id_carrera, int id_profesor) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("id_carrera", id_carrera);
        values.put("id_profesor", id_profesor);
        db.insertOrThrow("curso", null, values);
    }
    public void insertAlumno(String nombre,int id_usuario) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("id_usuario", id_usuario);
        db.insertOrThrow("alumno", null, values);
    }
    public void insertTarea(String nombre,int id_curso,String fecha) {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("id_curso", id_curso);
        values.put("fecha", fecha);
        db.insertOrThrow("tarea", null, values);
    }
// -------------------------------UPDATES -------------------------------------//
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
    public boolean deleteData(String id) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = "id" + "=?";
        String[] whereArgs = {id};

        int resultado = db.delete("tarea", whereClause, whereArgs);
        return resultado > 0;
    }



    public SQLiteDatabase getDb() {
        return baseDatos.getWritableDatabase();
    }


}
