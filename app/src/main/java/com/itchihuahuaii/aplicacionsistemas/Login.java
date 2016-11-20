package com.itchihuahuaii.aplicacionsistemas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.itchihuahuaii.aplicacionsistemas.sqlite.OperacionesBaseDatos;

import java.util.Calendar;
import java.util.Date;

public class Login extends AppCompatActivity {

    public OperacionesBaseDatos datos;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String fecha = Calendar.getInstance().getTime().toString();
        getApplicationContext().deleteDatabase("plataforma.db");
        datos=OperacionesBaseDatos.obtenerInstancia(getApplicationContext());
        try{
            datos.insertUsuario("gleish","123","ALUMNO");
            datos.insertUsuario("rafa","123","ALUMNO");
            datos.insertUsuario("waza","123","ALUMNO");
            datos.insertUsuario("profe","123","PROFESOR");
            datos.insertUsuario("boney","123","PROFESOR");

            datos.insertAlumno("Luis Fernando",1);
            datos.insertAlumno("Rafael",2);
            datos.insertAlumno("Miguel Angel",3);

            datos.insertProfesor("Chevere",4);
            datos.insertProfesor("Carlos Boney",5);

            datos.insertCarrera("Ingenieria en sistemas computacionales");

            datos.insertCurso("Programacion en Objetos",1,1);
            datos.insertCurso("Programacion en Moviles",1,1);
            datos.insertCurso("JavaScript Basico",1,2);
            datos.insertCurso("JavaScript Avanzado",1,2);

            datos.insertTarea("Tarea 1: Hola mundo",1,fecha);
            datos.insertTarea("Tarea 2: Variables",1,fecha);
            datos.insertTarea("Tarea 1: Android Estudio",2,fecha);
            datos.insertTarea("Tarea 1: Javascript Hola mundo",3,fecha);
            datos.insertTarea("Tarea 1: Configurar IDE",4,fecha);

            datos.insertAlumno_Curso(1,1);
            datos.insertAlumno_Curso(1,2);

        }catch(Exception e){
            System.out.println(e);
        }


        final EditText edt_user = (EditText)findViewById(R.id.login_user);
        final EditText edt_password = (EditText)findViewById(R.id.login_password);


        btn_login = (Button)findViewById(R.id.login_button);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tipo = datos.selectPlataformaDB("SELECT tipo FROM usuario WHERE nick='"+edt_user.getText().toString()+"' AND password='"+edt_password.getText().toString()+"'");
                if(!tipo.equals("")){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("USUARIO",edt_user.getText().toString());
                    String id_usuario = datos.selectPlataformaDB("SELECT id FROM usuario WHERE nick='"+edt_user.getText().toString()+"'");
                    String id_alumno = datos.selectPlataformaDB("SELECT id FROM alumno WHERE id_usuario="+id_usuario);
                    String id_profesor = datos.selectPlataformaDB("SELECT id FROM profesor WHERE id_usuario="+id_usuario);

                    intent.putExtra("ID_USUARIO",id_usuario);
                    intent.putExtra("ID_ALUMNO",id_alumno);
                    intent.putExtra("ID_PROFESOR",id_profesor);
                    intent.putExtra("TIPO",tipo);
                    startActivity(intent);
                }else {
                    Toast.makeText(Login.this, "Nombre o usuario incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
