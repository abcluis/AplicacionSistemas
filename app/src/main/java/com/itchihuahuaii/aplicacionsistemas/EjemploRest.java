package com.itchihuahuaii.aplicacionsistemas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class EjemploRest extends AppCompatActivity {

    private EditText nick,password,nombre,id;
    private Button btnDB;
    JSONArray jsonArray;
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=30000;

    ListView list;
    Button nuevo;
    ArrayList<HashMap<String, String>> nombres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejemplo_rest);
        btnDB = (Button)findViewById(R.id.boton1);
        list = (ListView)findViewById(R.id.list);
        nuevo = (Button)findViewById(R.id.nuevo);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("CLick",adapterView.getAdapter().getItem(i).toString());
                HashMap<String,String> map = (HashMap<String,String>)adapterView.getAdapter().getItem(i);
                list.setVisibility(View.GONE);
                try{

                for(int k=0;k<jsonArray.length();k++){
                    JSONObject jsonPost = jsonArray.getJSONObject(k);
                    if(jsonPost.getString("id").equals(map.get("id"))){
                        Toast.makeText(EjemploRest.this, "La informacion de array es\n"+jsonPost.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                }catch(JSONException e){}
                JSONObject obj=new JSONObject(map);
                Log.e("JSON",""+obj);
                Fragment aux = new FragmentEdit();
                Bundle bundle = new Bundle();
                bundle.putSerializable("map",map);
                aux.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_ejemplo_rest,aux).addToBackStack("JSON").commit();

            }
        });
        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment aux = new FragmentEdit();
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_ejemplo_rest,aux).addToBackStack("JSON").commit();
            }
        });
        btnDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.setVisibility(View.VISIBLE);
                new AsyncLogin().execute();
            }
        });
        /*nombre = (EditText)findViewById(R.id.nombre);
        id = (EditText)findViewById(R.id.id_usuario);*/
    }
    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(EjemploRest.this);
        HttpURLConnection conn;
        URL url = null;

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
            try {

                HttpURLConnection urlConnection = null;

                nombres = new ArrayList<>();

                try {

                    URL url = new URL("http://192.168.0.30:8081/lola/user/getAll");
                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("GET");

                    int reponse = urlConnection.getResponseCode();

                    if (reponse == HttpURLConnection.HTTP_OK) {

                        BufferedReader inStream = new BufferedReader(
                                new InputStreamReader(urlConnection.getInputStream()));

                        String inputLine = "";

                        StringBuffer buffer = new StringBuffer();

                        while ( (inputLine = inStream.readLine()) != null) {
                            buffer.append(inputLine);
                        }
                        inStream.close();

                        Log.i("Areglo", buffer.toString());

                        jsonArray = new JSONArray(buffer.toString());


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonPost = jsonArray.getJSONObject(i);
                            Log.e("JSON",jsonPost.toString());
                            HashMap<String, String> temporal = new HashMap<>();
                            temporal.put("id", jsonPost.getString("id"));
                            temporal.put("firstName", jsonPost.getString("firstName"));
                            temporal.put("lastName", jsonPost.getString("lastName"));
                            temporal.put("password", jsonPost.getString("password"));
                            temporal.put("reference", jsonPost.getString("reference"));
                            nombres.add(temporal);
                        }
                        Log.e("TODO","TODO HA SALIDO BIEN");
                    } else {
                        return null;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }

                // Enter URL address where your php file resides
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {

            pdLoading.dismiss();

            ListAdapter adapter = new SimpleAdapter(getApplicationContext(), nombres,
                    R.layout.list_item_json, new String[]{"firstName","lastName","password","reference"},
                    new int[]{R.id.nombre,R.id.apellido,R.id.password,R.id.referencia});

            list.setAdapter(adapter);

        }

    }
}
