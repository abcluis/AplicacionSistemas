package com.itchihuahuaii.aplicacionsistemas;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdaptadorCursoVista extends RecyclerView.Adapter<AdaptadorCursoVista.ViewHolder> {
    private final Context contexto;
    private Cursor items;
    public boolean ajeno=false;

    private OnItemClickListener escucha;

    interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idAlquiler);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // Referencias UI
        public TextView tarea;
        public ImageView delete;


        public ViewHolder(View v) {
            super(v);
            tarea = (TextView) v.findViewById(R.id.titulo_curso_vista);
            tarea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.moveToPosition(getAdapterPosition());
                    MainActivity mainActivity = (MainActivity)contexto;
                    mainActivity.setTarea(mainActivity.datos.selectPlataformaDB("SELECT id FROM tarea WHERE nombre='"+items.getString(0)+"'"));
                    mainActivity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.contenedor_principal,new FragmentTareaVista()).commit();
                }
            });
            delete =(ImageView)v.findViewById(R.id.delete_tarea);
            MainActivity mainActivity = (MainActivity)contexto;
            if(mainActivity.getTipo().equals("ALUMNO")|| ajeno){
                delete.setVisibility(View.GONE);
            }

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.moveToPosition(getAdapterPosition());
                    MainActivity mainActivity = (MainActivity)contexto;
                    if(mainActivity.datos.deleteData(mainActivity.datos.selectPlataformaDB("SELECT id FROM tarea WHERE nombre='"+items.getString(0)+"'"))){
                        Toast.makeText(mainActivity, "Se ha eliminado el registro", Toast.LENGTH_SHORT).show();
                        Fragment aux = mainActivity.getSupportFragmentManager().findFragmentByTag("curso");
                        mainActivity.getSupportFragmentManager().beginTransaction().detach(aux).attach(aux).commit();

                    }else {
                        Toast.makeText(mainActivity, "algo fallo", Toast.LENGTH_SHORT).show();


                    }


                }
            });
        }

    }


    public AdaptadorCursoVista(Context contexto) {
        this.contexto = contexto;
    }

    public void setAjeno(boolean ajeno){
        this.ajeno=ajeno;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_curso_vista, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        items.moveToPosition(position);

        //

        String s;

        // Asignación UI
        s = items.getString(0);
        holder.tarea.setText(s);




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