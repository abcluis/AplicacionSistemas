package com.itchihuahuaii.aplicacionsistemas;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentMisCursos extends Fragment {
    RecyclerView reciclador;
    LinearLayoutManager linearLayoutManager;
    AdaptadorMisCursos adaptador;

    public FragmentMisCursos() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mis_cursos,container,false);

        reciclador =(RecyclerView)view.findViewById(R.id.reciclador);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(linearLayoutManager);
        adaptador = new AdaptadorMisCursos(getContext());
        MainActivity ma = (MainActivity)getActivity();
        reciclador.setHasFixedSize(false);
        Cursor c;
        if(ma.getTipo().equals("ALUMNO")){
            c = ma.datos.selectPlataformaDBCursor("SELECT curso.nombre, profesor.nombre FROM curso,profesor,alumno,alumno_curso" +
                    " WHERE curso.id_profesor=profesor.id AND alumno_curso.id_curso=curso.id AND alumno_curso.id_alumno=alumno.id AND alumno.id="+ma.getId_alumno());
        }else {
            c = ma.datos.selectPlataformaDBCursor("SELECT curso.nombre,profesor.nombre FROM curso,profesor WHERE curso.id_profesor = profesor.id AND profesor.id="+ma.getId_profesor());
        }
        adaptador.swapCursor(c);
        reciclador.setAdapter(adaptador);
        return view;
    }
}