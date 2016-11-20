package com.itchihuahuaii.aplicacionsistemas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


public class FragmentNuevaTarea extends Fragment {


    public FragmentNuevaTarea() {
        // Required empty public constructor
    }

    Button btn_tarea;
    EditText edt_tarea;
    DatePicker fecha;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nueva_tarea, container, false);
        btn_tarea = (Button)view.findViewById(R.id.nueva_tarea_button);
        edt_tarea = (EditText)view.findViewById(R.id.nueva_tarea_nombre);
        fecha = (DatePicker)view.findViewById(R.id.datePicker);
        btn_tarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity principal = (MainActivity)getContext();
                principal.datos.insertTarea(edt_tarea.getText().toString(),principal.getCurso(),""+fecha.getDayOfMonth()+"/"+fecha.getMonth()+"/"+fecha.getYear());
                if(principal.datos.selectPlataformaDB("SELECT id FROM tarea WHERE nombre='"+edt_tarea.getText().toString()+"'").equals("")){
                    Toast.makeText(principal, "Los datos son incorrectos", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(principal, "Se ha agregado la tarea", Toast.LENGTH_SHORT).show();
                    Fragment aux = principal.getSupportFragmentManager().findFragmentByTag("curso");
                    principal.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,aux).addToBackStack("null").commit();
                }
            }
        });
        return view;
    }

}
