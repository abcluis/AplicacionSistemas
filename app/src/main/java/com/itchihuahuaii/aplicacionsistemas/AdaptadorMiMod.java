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
 * Adaptador para el Fragment Mi Mod
 * @author Equipo Plataforma Mod
 * @version v0.7
 */
public class AdaptadorMiMod extends RecyclerView.Adapter<AdaptadorMiMod.ViewHolder> {
    private final Context contexto;
    private Cursor items;

    private OnItemClickListener escucha;
    /**
     * Interfaz para el evento onClick
     */
    interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idAlquiler);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            {
        // Referencias UI
        public TextView carrera;
                /**
                 * Constructor donde se instancian los objetos del adaptador
                 * @param v Parametro tipo View
                 */
        public ViewHolder(View v) {
            super(v);
            carrera = (TextView) v.findViewById(R.id.titulo_carrera_mi_mod);
            carrera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.moveToPosition(getAdapterPosition());
                    MainActivity ap = (MainActivity) contexto;
                    Fragment cursocarrera = new FragmentCursoCarrera();
                    ap.setCarrera(items.getString(0));
                    ap.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_principal,cursocarrera,"curso_carrera").addToBackStack(null).commit();
                }
            });
        }

    }

    /**
     * Constructor
     * @param contexto contexto de la aplicacion
     */

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
        s = items.getString(0);
        holder.carrera.setText(s);


    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.getCount();
        return 0;
    }
    /**
     * Metodo que recibe el cursor para cargar los datos
     * @param nuevoCursor Cursor de SQL
     */
    public void swapCursor(Cursor nuevoCursor) {
        if (nuevoCursor != null) {
            items = nuevoCursor;
            notifyDataSetChanged();
        }
    }

    public Cursor getCursor() {
        return items;
    }


}