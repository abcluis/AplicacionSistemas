package com.itchihuahuaii.aplicacionsistemas;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentCursoCarrera extends Fragment {

    RecyclerView reciclador;
    LinearLayoutManager linearLayoutManager;
    AdaptadorCursoCarrera adaptador;
    TextView titulo;

    public FragmentCursoCarrera() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curso_carrera,container,false);
        titulo=(TextView) view.findViewById(R.id.titulo_cursos_por_carrera);
        Bundle bundle = getArguments();
        Toast.makeText(getContext(), bundle.getString("dato"), Toast.LENGTH_SHORT).show();
        String id = bundle.getString("dato");
        reciclador =(RecyclerView)view.findViewById(R.id.reciclador);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(linearLayoutManager);
        adaptador = new AdaptadorCursoCarrera(getContext());
        MainActivity ma = (MainActivity)getActivity();
        Cursor cursor = ma.datos.obtenerCursosCarrera(id);
        adaptador.swapCursor(cursor);
        reciclador.setAdapter(adaptador);
        return view;
    }

}
