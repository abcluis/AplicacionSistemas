package com.itchihuahuaii.aplicacionsistemas;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEdit extends Fragment {


    public FragmentEdit() {
        // Required empty public constructor
    }
    HashMap<String,String> map;
    EditText edt_nombre,edt_apellido,edt_password,edt_reference;
    Button guardar;
    int tipo;
    JSONObject json;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        edt_nombre = (EditText)view.findViewById(R.id.edt_nombre);
        edt_apellido = (EditText)view.findViewById(R.id.edt_apellido);
        edt_password = (EditText)view.findViewById(R.id.edt_password);
        edt_reference = (EditText)view.findViewById(R.id.edt_reference);

        guardar=(Button)view.findViewById(R.id.guardar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tipo==1){
                    new AsyncLogin(map).execute();
                }else {
                    try {
                        json.put("firstName", edt_nombre.getText().toString());
                        json.put("lastName", edt_apellido.getText().toString());
                        json.put("password", edt_password.getText().toString());
                        json.put("reference", edt_reference.getText().toString());
                    }catch(JSONException e){}

                    new AsyncLogin(json).execute();
                }

            }
        });
        if(getArguments().getInt("tipo")==1){
            tipo=1;
        }else {
            tipo=2;
        }
       try{
            map = (HashMap<String,String>)getArguments().getSerializable("map");
            json = new JSONObject(getArguments().getString("JSON"));
            edt_nombre.setText(json.getString("firstName"));
            edt_apellido.setText(json.getString("lastName"));
            edt_password.setText(json.getString("password"));
            edt_reference.setText(json.getString("reference"));
       }
        catch(Exception e){
            map=  new HashMap<>();
            map.put("enable","1");
            map.put("firstName","Miguel");
            map.put("lastName","Hidalgo");
            map.put("password","1234");
            map.put("reference","6142352189");
            map.put("user","FDA029");
        }
        return view;
    }
    private class AsyncLogin extends AsyncTask<String,String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(getContext());
        HttpURLConnection conn;
        URL url = null;

        JSONObject mapa;

        AsyncLogin(JSONObject json){
            mapa=json;
        }

        AsyncLogin(HashMap<String,String> map){
            mapa=new JSONObject(map);
            try{

                mapa.put("id",12);

                mapa.put("type","ADMIN");
                JSONArray aux = new JSONArray();
                JSONObject obj = new JSONObject();
                obj.put("id",1);
                JSONObject obj2 = new JSONObject();
                obj2.put("id",1);
                obj2.put("enabled",true);
                obj2.put("name","ADMIN");
                obj.put("permission",obj2);

                aux.put(obj);
                mapa.put("userPermissionMaps",aux);
            }catch (JSONException e){

            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            URL url;
            String response = "";
            try {
                if(tipo==1){
                    url = new URL("http://192.168.0.30:8081/lola/user/add");
                }else {
                    url = new URL("http://192.168.0.30:8081/lola/user/update");
                }

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                if(tipo==1){
                    conn.setRequestMethod("POST");
                }else if(tipo==2){
                    conn.setRequestMethod("PUT");
                }


                conn.setRequestProperty("Content-Type","application/json");

                conn.setDoInput(true);
                conn.setDoOutput(true);

            /*
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));*/

                DataOutputStream writer = new DataOutputStream(conn.getOutputStream ());
                writer.writeBytes(mapa.toString());

                writer.flush();
                writer.close();
                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response+=line;
                    }
                    response="Correcto";
                }
                else {
                    response=""+responseCode;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String m) {

            pdLoading.dismiss();
            Toast.makeText(getContext(), m, Toast.LENGTH_SHORT).show();
        }

    }

}
