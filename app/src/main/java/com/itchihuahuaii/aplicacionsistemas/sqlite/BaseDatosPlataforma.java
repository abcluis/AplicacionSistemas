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
        //TABLA USUARIO
        db.execSQL("CREATE TABLE usuario(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nick TEXT UNIQUE NOT NULL, password TEXT NOT NULL, tipo TEXT NOT NULL)");

        db.execSQL("CREATE TABLE carrera(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT UNIQUE NOT NULL)");

        db.execSQL("CREATE TABLE alumno(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL, id_usuario INTEGER UNIQUE NOT NULL REFERENCES usuario(id))");

        db.execSQL("CREATE TABLE profesor(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL, id_usuario INTEGER UNIQUE NOT NULL REFERENCES usuario(id))");

        db.execSQL("CREATE TABLE alumno_curso(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_alumno INTEGER NOT NULL REFERENCES alumno(id), id_curso INTEGER NOT NULL REFERENCES curso(id))");

        db.execSQL("CREATE TABLE curso(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL, id_carrera INTEGER NOT NULL REFERENCES carrera(id)," +
                "id_profesor INTEGER NOT NULL REFERENCES profesor(id))");

        db.execSQL("CREATE TABLE tarea(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT UNIQUE NOT NULL, id_curso INTEGER NOT NULL REFERENCES curso(id), fecha TEXT NOT NULL)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
