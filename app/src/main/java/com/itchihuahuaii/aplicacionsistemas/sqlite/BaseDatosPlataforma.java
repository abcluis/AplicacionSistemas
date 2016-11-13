package com.itchihuahuaii.aplicacionsistemas.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;

/**
 * Created by usuario1 on 11/3/2016.
 */

public class BaseDatosPlataforma extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "plataforma.db";

    private static final int VERSION_ACTUAL = 1;

    private final Context context;

    interface TABLAS {
        String ALUMNO = "alumno";
        String CARRERA = "carrera";
        String CURSO = "curso";
        String ALUMNO_CURSO = "alumno_curso";
        String TAREA = "tarea";
        String PROFESOR = "profesor";
    }

    interface Referencias {
        String ID_ALUMNO = String.format("REFERENCES %s(%s) ON DELETE CASCADE",
                TABLAS.ALUMNO, ContratoPlataforma.Alumno.ID);
        String ID_CURSO = String.format("REFERENCES %s(%s)",
                TABLAS.CURSO, ContratoPlataforma.Curso.ID);
        String ID_PROFESOR = String.format("REFERENCES %s(%s)",
                TABLAS.PROFESOR, ContratoPlataforma.Profesor.ID);
        String ID_CARRERA = String.format("REFERENCES %s(%s)",
                TABLAS.CARRERA, ContratoPlataforma.Carrera.ID);
    }

    public BaseDatosPlataforma(Context context) {
        super(context, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.context = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TABLA PROFESOR
        db.execSQL(String.format("CREATE TABLE %s" +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT UNIQUE NOT NULL," +
                        "%s TEXT NOT NULL)",
                TABLAS.PROFESOR,
                BaseColumns._ID,
                ContratoPlataforma.Profesor.ID,
                ContratoPlataforma.Profesor.NOMBRE));

        // TABLA ALUMNO
        db.execSQL(String.format("CREATE TABLE %s" +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT UNIQUE NOT NULL," +
                        "%s TEXT NOT NULL)",
                TABLAS.ALUMNO,
                BaseColumns._ID,
                ContratoPlataforma.Alumno.ID,
                ContratoPlataforma.Alumno.NOMBRE));

        // TABLA ALUMNO-CURSO
        db.execSQL(String.format("CREATE TABLE %s(" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT UNIQUE NOT NULL," +
                        "%s TEXT NOT NULL %s," +
                        "%s TEXT NOT NULL %s)",
                TABLAS.ALUMNO_CURSO,
                BaseColumns._ID,
                ContratoPlataforma.AlumnoCurso.ID,
                ContratoPlataforma.AlumnoCurso.ID_ALUMNO, Referencias.ID_ALUMNO,
                ContratoPlataforma.AlumnoCurso.ID_CURSO, Referencias.ID_CURSO));

        // TABLA CURSO
        db.execSQL(String.format("CREATE TABLE %s(" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT UNIQUE NOT NULL," +
                        "%s TEXT NOT NULL," +
                        "%s TEXT NOT NULL %s," +
                        "%s TEXT NOT NULL %s)",
                TABLAS.CURSO, BaseColumns._ID, ContratoPlataforma.Curso.ID,
                ContratoPlataforma.Curso.NOMBRE,
                ContratoPlataforma.Curso.ID_CARRERA, Referencias.ID_CARRERA,
                ContratoPlataforma.Curso.ID_PROFESOR, Referencias.ID_PROFESOR));

        // TABLA CARRERA
        db.execSQL(String.format("CREATE TABLE %s(" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT UNIQUE NOT NULL," +
                        "%s TEXT NOT NULL)",
                TABLAS.CARRERA,
                BaseColumns._ID,
                ContratoPlataforma.Carrera.ID,
                ContratoPlataforma.Carrera.NOMBRE));

        // TABLA TAREA
        db.execSQL(String.format("CREATE TABLE %s(" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT UNIQUE NOT NULL," +
                        "%s TEXT NOT NULL," +
                        "%s TEXT NOT NULL %s)",
                TABLAS.TAREA,
                BaseColumns._ID,
                ContratoPlataforma.Tarea.ID,
                ContratoPlataforma.Tarea.NOMBRE,
                ContratoPlataforma.Tarea.ID_CURSO,Referencias.ID_CURSO));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLAS.CARRERA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLAS.CURSO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLAS.ALUMNO_CURSO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLAS.TAREA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLAS.ALUMNO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLAS.PROFESOR);
    }
}
