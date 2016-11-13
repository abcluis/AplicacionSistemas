package com.itchihuahuaii.aplicacionsistemas;

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
        System.out.println("DATO  USUARIO\n\n"+ ma.USER);
        adaptadorMisTareas.swapCursor(ma.datos.obtenerMisTareas(ma.USER));
        reciclador.setAdapter(adaptadorMisTareas);
        return view;
    }

}
