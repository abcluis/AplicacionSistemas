package com.itchihuahuaii.aplicacionsistemas;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.itchihuahuaii.aplicacionsistemas.sqlite.OperacionesBaseDatos;
/**
 * Actividad Principal donde se controlan todos los fragment
 * @author Equipo Plataforma Mod
 * @version v0.7
 */
public class MainActivity extends AppCompatActivity {

    public OperacionesBaseDatos datos;

    private String usuario,carrera,tarea,tipo;
    private int id_alumno,id_profesor,curso;



    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    public int getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor) {
        this.id_profesor = id_profesor;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    @Override

    protected void onDestroy() {
        datos.getDb().close();
        super.onDestroy();
    }

    DrawerLayout drawer;

    // Variables de la notificacion
    NotificationManager nm;
    Notification notif;
    static String ns = Context.NOTIFICATION_SERVICE;

    //Defino los iconos de la notificacion en la barra de notificacion
    int icono_v = R.drawable.notification;
    int icono_r = R.drawable.notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        agregarToolbar();
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        usuario = getIntent().getExtras().getString("USUARIO");
        tipo = getIntent().getExtras().getString("TIPO");
        if(tipo.equals("ALUMNO")){
            id_alumno=Integer.parseInt(getIntent().getExtras().getString("ID_ALUMNO"));
        }else if(tipo.equals("PROFESOR")){
            id_profesor=Integer.parseInt(getIntent().getExtras().getString("ID_PROFESOR"));
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            prepararDrawer(navigationView);
            seleccionarItem(navigationView.getMenu().getItem(0));
        }

        datos = OperacionesBaseDatos.obtenerInstancia(getApplicationContext());
        Cursor c;
        if(getTipo().equals("ALUMNO")){
            c=datos.selectPlataformaDBCursor("SELECT tarea.nombre, curso.nombre FROM tarea,curso,alumno_curso,alumno " +
                    "WHERE tarea.id_curso=curso.id AND alumno_curso.id_curso=curso.id AND alumno_curso.id_alumno=alumno.id AND alumno.id="+getId_alumno());
            Notification.Builder builder = new Notification.Builder(getApplicationContext());
            NotificationManager notifManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            notifManager.notify(1,getInboxStyle(builder,c));
        }else{
            c =datos.selectPlataformaDBCursor("SELECT tarea.nombre, curso.nombre FROM tarea,curso,profesor WHERE " +
                    "tarea.id_curso=curso.id AND curso.id_profesor=profesor.id AND profesor.id="+getId_profesor());
        }

    }
    private Notification getInboxStyle(Notification.Builder builder,Cursor c) {

        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.icon_tarea);
                builder
                .setContentTitle("Reduced Inbox title")
                .setContentText("Reduced content")
                .setContentInfo("Info")
                .setSmallIcon(R.drawable.icon_tarea)
                .setLargeIcon(bitmap);

        Notification.InboxStyle n = new Notification.InboxStyle(builder)
                .setBigContentTitle("Mis Tareas Pendientes");
        c.moveToFirst();
        do{
            n.addLine(c.getString(0));
        }
        while(c.moveToNext());


        return n.build();
    }


    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_white);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void seleccionarItem(MenuItem item) {
        Fragment fragment = null;
        FragmentManager fm = getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.menu_inicio:
                fragment = new FragmentoInicio();
                break;
        }
        if (fragment != null) {
            fm.beginTransaction().replace(R.id.contenedor_principal, fragment).commit();

        }
        setTitle(item.getTitle());
    }

}
