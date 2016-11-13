package com.itchihuahuaii.aplicacionsistemas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by usuario1 on 11/4/2016.
 */
public class AdaptadorMiMod extends RecyclerView.Adapter<AdaptadorMiMod.ViewHolder> {
    private final Context contexto;
    private Cursor items;

    private OnItemClickListener escucha;

    interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idAlquiler);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            {
        // Referencias UI
        public TextView carrera;


        public ViewHolder(View v) {
            super(v);
            carrera = (TextView) v.findViewById(R.id.titulo_carrera_mi_mod);
            carrera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.moveToPosition(getAdapterPosition());
                    MainActivity ap = (MainActivity) contexto;
                    Fragment cursocarrera = new FragmentCursoCarrera();
                    Bundle bundle = new Bundle();
                    bundle.putString("dato",items.getString(1));
                    cursocarrera.setArguments(bundle);
                    ap.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,cursocarrera).commit();
                }
            });
        }

    }

    private String obtenerIdAlquiler(int posicion) {
        if (items != null) {
            if (items.moveToPosition(posicion)) {
                return items.getString(ConsultaAlquileres.ID_ALQUILER);
            }
        }

        return null;
    }

    public AdaptadorMiMod(Context contexto) {
        this.contexto = contexto;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_mi_mod, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        items.moveToPosition(position);

        String s;

        // Asignación UI
        s = items.getString(ConsultaAlquileres.NOMBRE);
        holder.carrera.setText(s);


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

    interface ConsultaAlquileres {
        int ID_ALQUILER = 1;
        int NOMBRE = 2;
        int UBICACION = 3;
        int DESCRIPCION = 4;
        int PRECIO = 5;
        int URL = 6;
    }
}