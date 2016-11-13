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
import android.widget.TextView;

import org.w3c.dom.Text;


public class FragmentCursoVista extends Fragment {
    RecyclerView reciclador;
    GridLayoutManager linearLayoutManager;
    AdaptadorMisTareas adaptadorMisTareas;
    TextView titulo_curso;

    public FragmentCursoVista() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curso_vista,container,false);

        titulo_curso = (TextView)view.findViewById(R.id.curso_vista_nombre_curso);
        reciclador =(RecyclerView)view.findViewById(R.id.reciclador);
        linearLayoutManager = new GridLayoutManager(getActivity(),2);
        reciclador.setLayoutManager(linearLayoutManager);
        adaptadorMisTareas = new AdaptadorMisTareas(getContext());
        MainActivity ma = (MainActivity)getActivity();
        Cursor c = ma.datos.obtenerCursoDatos(ma.CURSO);
        adaptadorMisTareas.swapCursor(c);
        c.moveToFirst();
        titulo_curso.setText(c.getString(0));
        reciclador.setAdapter(adaptadorMisTareas);
        return view;
    }

}
