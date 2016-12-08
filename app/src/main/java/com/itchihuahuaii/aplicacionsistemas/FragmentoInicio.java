package com.itchihuahuaii.aplicacionsistemas;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itchihuahuaii.aplicacionsistemas.sqlite.OperacionesBaseDatos;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment que contiene las tab de la aplicacion
 * @author Equipo Plataforma Mod
 * @version v0.7
 */
public class FragmentoInicio extends Fragment {

    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public FragmentoInicio(){}

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
        
        View view = inflater.inflate(R.layout.fragmento_inicio,container,false);
        if(savedInstanceState==null){
            insertarTabs(container);
            viewPager = (ViewPager)view.findViewById(R.id.pager);
            poblarViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }

        return view;
    }
    /**
     * Metodo donde se insertar las pestañas al fragment
     * @param container
     */
    private void insertarTabs(ViewGroup container) {
        View padre=(View)container.getParent();
        appBarLayout =(AppBarLayout)padre.findViewById(R.id.appBar);
        tabLayout=new TabLayout(getActivity());
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"),Color.parseColor("#FFFFFF"));
        appBarLayout.addView(tabLayout);
    }
    /**
     * Metodo donde se asignan los fragment que tendran las comidas
     * @param viewPager ViewPager declarado en el layout
     */
    private void poblarViewPager(ViewPager viewPager) {
        AdaptadorSecciones adapter = new AdaptadorSecciones(getChildFragmentManager());
        Fragment mimod = new FragmentMiMod();
        mimod.setArguments(getArguments());
        MainActivity mainActivity = (MainActivity)getContext();

            adapter.addFragment(new FragmentMiMod(), getString(R.string.titulo_tab_mimod));
            adapter.addFragment(new FragmentMisTareas(), getString(R.string.titulo_tab_mistareas));
            adapter.addFragment(new FragmentMisCursos(), getString(R.string.titulo_tab_miscursos));

        viewPager.setAdapter(adapter);
    }
    public void onDestroyView() {
        super.onDestroyView();
        appBarLayout.removeView(tabLayout);
    }
    /**
     * Clase donde se añaden las pestañas
     */
    public class AdaptadorSecciones extends FragmentStatePagerAdapter {
        private final List<Fragment> fragmentos = new ArrayList<>();
        private final List<String> titulosFragmentos = new ArrayList<>();

        public FragmentManager fm;
        /**
         * Constructor
         * @param fm FragmentManager para hacer las transiciones
         */
        public AdaptadorSecciones(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            fragmentos.add(fragment);
            titulosFragmentos.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titulosFragmentos.get(position);
        }
    }


}
