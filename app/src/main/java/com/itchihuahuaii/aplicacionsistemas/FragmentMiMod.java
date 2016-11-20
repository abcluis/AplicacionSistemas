package com.itchihuahuaii.aplicacionsistemas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.itchihuahuaii.aplicacionsistemas.sqlite.BaseDatosPlataforma;

public class FragmentMiMod extends Fragment {
    RecyclerView reciclador;
    LinearLayoutManager linearLayoutManager;
    AdaptadorMiMod adaptadorMiMod;

    public FragmentMiMod() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_mi_mod,container,false);

        reciclador =(RecyclerView)view.findViewById(R.id.reciclador);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(linearLayoutManager);
        adaptadorMiMod = new AdaptadorMiMod(getContext());
        MainActivity ma = (MainActivity)getActivity();

        adaptadorMiMod.swapCursor(ma.datos.selectPlataformaDBCursor("SELECT nombre FROM carrera"));
        reciclador.setAdapter(adaptadorMiMod);
        return view;
    }

}
