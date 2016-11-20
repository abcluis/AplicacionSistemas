package com.itchihuahuaii.aplicacionsistemas;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class FragmentCursoVista extends Fragment {
    RecyclerView reciclador;
    LinearLayoutManager linearLayoutManager;
    AdaptadorCursoVista adaptadorMisTareas;
    TextView titulo_curso;
    FloatingActionButton nueva_tarea;

    public FragmentCursoVista() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curso_vista,container,false);

        titulo_curso = (TextView)view.findViewById(R.id.curso_vista_nombre_curso);
        reciclador =(RecyclerView)view.findViewById(R.id.reciclador);
        nueva_tarea = (FloatingActionButton)view.findViewById(R.id.fab);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(linearLayoutManager);
        adaptadorMisTareas = new AdaptadorCursoVista(getContext());
        MainActivity ma = (MainActivity)getActivity();
        String cursoAjeno="";
        if(ma.getTipo().equals("PROFESOR")){
            cursoAjeno = ma.datos.selectPlataformaDB("SELECT curso.id FROM curso,profesor,carrera WHERE curso.id_profesor=profesor.id AND " +
                    "profesor.id="+ma.getId_profesor()+" AND curso.id="+ma.getCurso());
            if (cursoAjeno.equals("")) {
                adaptadorMisTareas.setAjeno(true);
            }

        }
        if(ma.getTipo().equals("ALUMNO") || cursoAjeno.equals("")){
            nueva_tarea.setVisibility(View.GONE);
        }
        nueva_tarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity principal = (MainActivity) getContext();
                principal.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,new FragmentNuevaTarea(),"nueva_tarea").addToBackStack(null).commit();
            }
        });
        Cursor c = ma.datos.selectPlataformaDBCursor("SELECT tarea.nombre FROM tarea,curso WHERE tarea.id_curso=curso.id" +
                " AND curso.id="+ma.getCurso());
        adaptadorMisTareas.swapCursor(c);
        titulo_curso.setText(ma.datos.selectPlataformaDB("SELECT nombre FROM curso WHERE id="+ma.getCurso()));
        reciclador.setAdapter(adaptadorMisTareas);
        return view;
    }

}
