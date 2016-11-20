package com.itchihuahuaii.aplicacionsistemas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by usuario1 on 11/4/2016.
 */
public class AdaptadorCursoCarrera extends RecyclerView.Adapter<AdaptadorCursoCarrera.ViewHolder> {
    private final Context contexto;
    private Cursor items;


    interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idAlquiler);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // Referencias UI
        public TextView nombre_profesor;
        public TextView nombre_curso;
        ImageView nuevo_curso;

        public ViewHolder(View v) {
            super(v);
            nombre_profesor = (TextView) v.findViewById(R.id.nombre_profesor);
            nombre_curso = (TextView)v.findViewById(R.id.nombre_curso);
            nombre_curso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.moveToPosition(getAdapterPosition());
                    MainActivity ma= (MainActivity)contexto;
                    ma.setCurso(Integer.parseInt(ma.datos.selectPlataformaDB("SELECT id FROM curso WHERE nombre='"+items.getString(0)+"'")));
                    ma.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,new FragmentCursoVista(),"curso").addToBackStack(null).commit();

                }
            });
            nuevo_curso = (ImageView)v.findViewById(R.id.nuevo_curso);

            nuevo_curso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.moveToPosition(getAdapterPosition());
                    MainActivity principal = (MainActivity)contexto;
                    String idCurso = principal.datos.selectPlataformaDB("SELECT id FROM curso WHERE nombre='"+items.getString(0)+"'");
                    principal.datos.insertAlumno_Curso(principal.getId_alumno(),Integer.parseInt(idCurso));
                    Fragment fragment = principal.getSupportFragmentManager().findFragmentByTag("curso_carrera");
                    Toast.makeText(principal, "Te haz matriculado en el curso", Toast.LENGTH_SHORT).show();
                    principal.getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
                }
            });


        }

    }



    public AdaptadorCursoCarrera(Context contexto) {
        this.contexto = contexto;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_cursos, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        items.moveToPosition(position);

        String s;

        // Asignación UI
        s = items.getString(0);
        holder.nombre_curso.setText(s);

        s = items.getString(1);
        holder.nombre_profesor.setText(s);

        MainActivity principal = (MainActivity)contexto;

        String idCurso = principal.datos.selectPlataformaDB("SELECT id FROM curso WHERE nombre='"+items.getString(0)+"'");
        String dato = principal.datos.selectPlataformaDB("SELECT curso.nombre FROM curso,alumno_curso,alumno WHERE alumno_curso.id_curso="+idCurso+" AND alumno_curso.id_alumno="+principal.getId_alumno());

        if(!dato.equals("") || !principal.getTipo().equals("ALUMNO")){
            holder.nuevo_curso.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.getCount();
        return 0;
    }

    public void swapCursor(Cursor nuevoCursor) {
        if (nuevoCursor != null) {
            items = nuevoCursor;
            notifyDataSetChanged();
        }
    }

    public Cursor getCursor() {
        return items;
    }

    interface columnas {

    }
}