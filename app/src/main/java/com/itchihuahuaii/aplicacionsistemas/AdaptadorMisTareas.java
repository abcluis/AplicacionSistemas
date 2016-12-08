package com.itchihuahuaii.aplicacionsistemas;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * Adaptador para el Fragment Mis Tareas
 * @author Equipo Plataforma Mod
 * @version v0.7
 */
public class AdaptadorMisTareas extends RecyclerView.Adapter<AdaptadorMisTareas.ViewHolder> {
    private final Context contexto;
    private Cursor items;

    private OnItemClickListener escucha;
    /**
     * Interfaz para el evento onClick
     */
    interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idAlquiler);
    }
    /**
     * Clase View Holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // Referencias UI
        public TextView curso;
        public TextView tarea;

        /**
         * Constructor donde se instancian los objetos del adaptador
         * @param v Parametro tipo View
         */
        public ViewHolder(View v) {
            super(v);
            curso = (TextView) v.findViewById(R.id.titulo_curso_mis_tareas);
            tarea=(TextView)v.findViewById(R.id.titulo_tarea_mis_tareas);
            tarea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.moveToPosition(getAdapterPosition());
                    MainActivity mainActivity = (MainActivity)contexto;
                    mainActivity.setTarea(mainActivity.datos.selectPlataformaDB("SELECT id FROM tarea WHERE nombre='"+items.getString(0)+"'"));
                    mainActivity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.contenedor_principal,new FragmentTareaVista()).commit();
                }
            });
        }

    }

    /**
     * Constructor
     * @param contexto contexto de la aplicacion
     */
    public AdaptadorMisTareas(Context contexto) {
        this.contexto = contexto;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_mis_tareas, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        items.moveToPosition(position);

        String s;

        // Asignación UI
        s = items.getString(1);
        holder.curso.setText(s);

        s = items.getString(0);
        holder.tarea.setText(s);



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

    interface datos {
        int NOMBRE_USUARIO = 0;
        int TITULO = 1;
        int NOMBRE_CURSO = 2;
    }
}