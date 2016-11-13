package com.itchihuahuaii.aplicacionsistemas;

import android.database.DatabaseUtils;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

public class MainActivity extends AppCompatActivity {

    public OperacionesBaseDatos datos;

    public String USER;
    public String CURSO;
    public String TAREA;


    public class PruebasActivity extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            String alumno1;
            String alumno2;
            String tarea1;
            String curso1;
            String profesor1;
            String carreraISC;
            try {


                alumno1 = datos.insertarAlumno("Luis");
                alumno2 = datos.insertarAlumno("David");
                String alumno3 = datos.insertarAlumno("Angel");
                String alumno4 = datos.insertarAlumno("Karely");
                String alumno5 = datos.insertarAlumno("Rafael");

                profesor1 = datos.insertarProfesor("Carlos Boney");
                String profesor2 = datos.insertarProfesor("Chevere");
                String profesor3 = datos.insertarProfesor("Nachito");
                String profesor4 = datos.insertarProfesor("Martin Gonzalez");

                carreraISC = datos.insertarCarrera("Ingenieria Sistemas Computacionales");
                String carreraIIF = datos.insertarCarrera("Ingenieria en Informatica");

                curso1 = datos.insertarCurso("Sistemas Operativos Moviles", carreraISC, profesor2);
                String curso2 = datos.insertarCurso("Administracion de Servidores", carreraISC, profesor4);
                String curso3 = datos.insertarCurso("Graficacion", carreraISC, profesor1);
                String curso4 = datos.insertarCurso("Principios de Programacion", carreraISC, profesor1);

                String alumnoCurso1 = datos.insertarAlumnoCurso(alumno1, curso1);
                String alumnoCurso2 = datos.insertarAlumnoCurso(alumno1, curso2);
                String alumnoCurso3 = datos.insertarAlumnoCurso(alumno2, curso1);
                String alumnoCurso4 = datos.insertarAlumnoCurso(alumno2, curso3);

                tarea1 = datos.insertarTarea("Documentacion 1", curso1);
                String tarea2 = datos.insertarTarea("Buenas practicas Servidores", curso2);
                String tarea3 = datos.insertarTarea("Documentacion Wi fi", curso1);
                String tarea4 = datos.insertarTarea("Elaboracion de un juego", curso3);
                String tarea5 = datos.insertarTarea("Elaboracion de un mp3", curso4);

                USER = alumno2;

            } finally {

            }
            Log.d("Detalles de pedido", "Detalles del curso");
            DatabaseUtils.dumpCursor(datos.obtenerCursoDatos(curso1));

            return null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Toast.makeText(getApplicationContext(), "Voy hacia atrás!!",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        datos.getDb().close();
        super.onDestroy();
    }

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        agregarToolbar();
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            prepararDrawer(navigationView);
            seleccionarItem(navigationView.getMenu().getItem(0));
        }

        getApplicationContext().deleteDatabase("plataforma.db");
        datos = OperacionesBaseDatos.obtenerInstancia(getApplicationContext());

        new PruebasActivity().execute();
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
                Bundle bundle = new Bundle();
                bundle.putString("user", USER);
                fragment.setArguments(bundle);
                break;
        }
        if (fragment != null) {
            fm.beginTransaction().replace(R.id.contenedor_principal, fragment).commit();

        }
        setTitle(item.getTitle());
    }

}
