package com.itchihuahuaii.aplicacionsistemas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


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
                int day = fecha.getDayOfMonth();
                int month = fecha.getMonth();
                int year =  fecha.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String dateString = sdf.format(calendar.getTime());
                MainActivity principal = (MainActivity)getContext();
                if(!edt_tarea.getText().toString().equals("")){
                    principal.datos.insertTarea(edt_tarea.getText().toString(),principal.getCurso(),""+fecha.getDayOfMonth()+"/"+fecha.getMonth()+"/"+fecha.getYear());
                }else{
                    Toast.makeText(principal, "Ingresa un nombre para la tarea", Toast.LENGTH_SHORT).show();
                }
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
