package com.itchihuahuaii.aplicacionsistemas;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentMisTareas extends Fragment {

    RecyclerView reciclador;
    GridLayoutManager linearLayoutManager;
    AdaptadorMisTareas adaptadorMisTareas;

    public FragmentMisTareas() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mis_tareas,container,false);

        reciclador =(RecyclerView)view.findViewById(R.id.reciclador);
        linearLayoutManager = new GridLayoutManager(getActivity(),2);
        reciclador.setLayoutManager(linearLayoutManager);
        adaptadorMisTareas = new AdaptadorMisTareas(getContext());
        MainActivity ma = (MainActivity)getActivity();
        Cursor c;
        if(ma.getTipo().equals("ALUMNO")){
            c=ma.datos.selectPlataformaDBCursor("SELECT tarea.nombre, curso.nombre FROM tarea,curso,alumno_curso,alumno " +
                    "WHERE tarea.id_curso=curso.id AND alumno_curso.id_curso=curso.id AND alumno_curso.id_alumno=alumno.id AND alumno.id="+ma.getId_alumno());
        }else{
            c =ma.datos.selectPlataformaDBCursor("SELECT tarea.nombre, curso.nombre FROM tarea,curso,profesor WHERE " +
                    "tarea.id_curso=curso.id AND curso.id_profesor=profesor.id AND profesor.id="+ma.getId_profesor());
        }

        adaptadorMisTareas.swapCursor(c);
        reciclador.setAdapter(adaptadorMisTareas);
        return view;
    }

}
