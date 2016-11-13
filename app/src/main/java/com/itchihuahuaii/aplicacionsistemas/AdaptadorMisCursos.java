package com.itchihuahuaii.aplicacionsistemas;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdaptadorMisCursos extends RecyclerView.Adapter<AdaptadorMisCursos.ViewHolder> {
    private final Context contexto;
    private Cursor items;

    private OnItemClickListener escucha;

    interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idAlquiler);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Referencias UI
        public TextView curso;
        public TextView profesor;


        public ViewHolder(View v) {
            super(v);
            curso = (TextView) v.findViewById(R.id.nombre_curso);
            profesor = (TextView) v.findViewById(R.id.nombre_profesor);
            curso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.moveToPosition(getAdapterPosition());
                    MainActivity ma= (MainActivity)contexto;
                    ma.CURSO=items.getString(2);
                    System.out.println("CURSO PRESIONADO ---------------------"+ma.CURSO);
                    ma.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,new FragmentCursoVista()).commit();
                }
            });
        }

    }


    public AdaptadorMisCursos(Context contexto) {
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
        holder.curso.setText(s);

        s = items.getString(1);
        holder.profesor.setText(s);


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

    interface datos {
        int NOMBRE_USUARIO = 0;
        int TITULO = 1;
        int NOMBRE_CURSO = 2;
    }
}
