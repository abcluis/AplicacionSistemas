package com.itchihuahuaii.aplicacionsistemas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Fragment que muestra las vista de tarea
 * @author Equipo Plataforma Mod
 * @version v0.7
 */
public class FragmentTareaVista extends Fragment {

    public FragmentTareaVista() {
        // Required empty public constructor
    }


    TextView tarea;
    TextView fecha;
    /**
     * Metodo con la vista del fragment
     * @param inflater Inflater
     * @param container contenedor
     * @param savedInstanceState instancia
     * @return Devuelve la vista del fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tarea_vista, container, false);

        tarea = (TextView) view.findViewById(R.id.vista_tarea_nombre);
        fecha=(TextView)view.findViewById(R.id.tarea_vista_fecha);
        MainActivity mainActivity = (MainActivity)getContext();
        tarea.setText(mainActivity.datos.selectPlataformaDB("SELECT nombre FROM tarea WHERE id="+mainActivity.getTarea()));
        fecha.setText("Fecha Entrega:\n"+mainActivity.datos.selectPlataformaDB("SELECT fecha FROM tarea WHERE id="+mainActivity.getTarea()));
        return view;

    }

}
