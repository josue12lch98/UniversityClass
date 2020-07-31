package com.example.universityclass.ViewModels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.universityclass.entidades.Profesores;
import com.google.gson.Gson;

import java.util.Arrays;

public class CompararJsonViewModel extends ViewModel {

    private MutableLiveData<Profesores[]> lista = new MutableLiveData<>();



    private Thread thread;

    public void iniciarComparacion(final Context context, final Profesores[] arregloAsesores) {

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://192.168.0.5:3000/profesores";
                StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Gson gson = new Gson();
                               Profesores[] listaProfesoresLocal = gson.fromJson(response, Profesores[].class);

                                if (!(Arrays.deepEquals(arregloAsesores, listaProfesoresLocal))) {

                                    lista.postValue(listaProfesoresLocal);

                                }


                                }


                            }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", "errorInterno");
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);



            }
        });


        thread.start();
    }

    public void detenerContador(){
        thread.interrupt();
    }


    public MutableLiveData<Profesores[]> getLista() {
        return lista;
    }


}
