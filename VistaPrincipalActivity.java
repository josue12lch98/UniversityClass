package com.example.universityclass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.universityclass.entidades.Profesores;
import com.example.universityclass.entidades.RolUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class VistaPrincipalActivity extends AppCompatActivity {
    Profesores[] lista;
    int rol_idrol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_principal);
        RolUser rolUser=leerArchivoTextRol();
         rol_idrol=rolUser.getRol_idrol();


     /*   Profesores[] arregloAsesores =leerArchivoTextoProfesores ("arregloAsesores");
        if (arregloAsesores == null) {
            //    ImageView imageView = findViewById(R.id.imageView);
            //   Picasso.get().load("https://files.pucp.education/homepucp/uploads/2020/05/12140640/afc_taller_clases-virtuales-zoom.jpg").into(imageView);
            String url = "https://apps-32c2d.firebaseio.com/UniversityClass/profesores.json";
            StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson = new Gson();
                            lista = gson.fromJson(response, Profesores[].class);
                            ListaUsuariosAdapter adapter=new ListaUsuariosAdapter(lista,VistaPrincipalActivity.this);
                            RecyclerView recyclerView=findViewById(R.id.RecyclerViewProfesores);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(VistaPrincipalActivity.this));
                            guardarComoTexto(lista);
                            Log.d("Usuarios", response);
                            Log.d("Usuarios2", lista.toString());

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Usuarios2", "error");
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else{
            Log.e("Arreglo Asesores",arregloAsesores.toString());
            ListaUsuariosAdapter adapter=new ListaUsuariosAdapter(arregloAsesores,VistaPrincipalActivity.this);
            RecyclerView recyclerView=findViewById(R.id.RecyclerViewProfesores);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(VistaPrincipalActivity.this));
            CompararJsonViewModel compararJsonViewModel=new ViewModelProvider(this).get(CompararJsonViewModel.class);
            compararJsonViewModel.getLista().observe(this, new Observer<Profesores[]>() {
                @Override
                public void onChanged(Profesores[] arregloAsesores) {
                    Log.d("arreglo","Cambio arreglo de asesores" );
                    guardarComoTexto(arregloAsesores);
                    ListaUsuariosAdapter adapter=new ListaUsuariosAdapter(arregloAsesores,VistaPrincipalActivity.this);
                    RecyclerView recyclerView=findViewById(R.id.RecyclerViewProfesores);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(VistaPrincipalActivity.this));
                    Toast.makeText(VistaPrincipalActivity.this,"Actualizando Asesores",Toast.LENGTH_SHORT).show();
                }
            });
            compararJsonViewModel.iniciarComparacion(this,arregloAsesores);
            Log.d("Usuarios", "Arreglo de Asesores obtenidos");


        }*/

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UniversityClass");

        Profesores[] arregloAsesores = leerArchivoTextoProfesores("arregloAsesores");
        if (arregloAsesores == null) {

            databaseReference.child("Profesores").addChildEventListener(new ChildEventListener() {


                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Long lon = (dataSnapshot.getChildrenCount());
                    int ii = lon.intValue();
                    Profesores[] profesores = new Profesores[ii];
                    int i = 0;

                    for (DataSnapshot children : dataSnapshot.getChildren()) {
                        Log.d("Usuarios", dataSnapshot.getValue().toString());
                        Profesores profesor = children.getValue(Profesores.class);
                        profesor.setKey(children.getKey());
                        Array.set(profesores, i, profesor);

                        i++;
                    }
                    lista = profesores;
                    ListaUsuariosAdapter adapter = new ListaUsuariosAdapter(lista, VistaPrincipalActivity.this);
                    RecyclerView recyclerView = findViewById(R.id.RecyclerViewProfesores);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(VistaPrincipalActivity.this));
                    guardarComoTexto(lista);
                    Log.d("Usuarios", dataSnapshot.getValue().toString());
                    Log.d("Usuarios2", lista.toString());

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
             /*   Long lon=(dataSnapshot.getChildrenCount ());
                int ii = lon.intValue();
                Profesores[] profesores =new Profesores[ii];
                int i=0;

                for (DataSnapshot children:dataSnapshot.getChildren() ){
                    Log.d("errorExtraño",dataSnapshot.getKey());
                    Gson gson=new Gson();
                    Log.d("errorExtraño",gson.toJson(dataSnapshot.getValue()));
                    Profesores profesor =children.getValue(Profesores.class);
                    profesor.setKey(Integer.parseInt(children.getKey()));
                    Array.set(profesores,i, profesor);

                    i++;
                }
                lista = profesores;
                ListaUsuariosAdapter adapter=new ListaUsuariosAdapter(lista,VistaPrincipalActivity.this);
                RecyclerView recyclerView=findViewById(R.id.RecyclerViewProfesores);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(VistaPrincipalActivity.this));
                guardarComoTexto(lista);
                Log.d("Usuarios", dataSnapshot.getValue().toString());
                Log.d("Usuarios2", lista.toString());
                notificacionCambioDeProfesor();
*/
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });





            /*

             */
        } else {
            Log.e("Arreglo Asesores", arregloAsesores.toString());
            ListaUsuariosAdapter adapter = new ListaUsuariosAdapter(arregloAsesores, VistaPrincipalActivity.this);
            RecyclerView recyclerView = findViewById(R.id.RecyclerViewProfesores);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(VistaPrincipalActivity.this));

            databaseReference.child("Profesores").addChildEventListener(new ChildEventListener() {


                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Long lon = (dataSnapshot.getChildrenCount());
                    int ii = lon.intValue();
                    Profesores[] profesores = new Profesores[ii];
                    int i = 0;

                    for (DataSnapshot children : dataSnapshot.getChildren()) {
                        Log.d("Usuarios", dataSnapshot.getValue().toString());
                        Profesores profesor = children.getValue(Profesores.class);

                        profesor.setKey(children.getKey());
                        Array.set(profesores, i, profesor);

                        i++;
                    }
                    lista = profesores;
                    ListaUsuariosAdapter adapter = new ListaUsuariosAdapter(lista, VistaPrincipalActivity.this);
                    RecyclerView recyclerView = findViewById(R.id.RecyclerViewProfesores);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(VistaPrincipalActivity.this));
                    guardarComoTexto(lista);
                    Log.d("Usuarios", dataSnapshot.getValue().toString());
                    Log.d("Usuarios2", lista.toString());

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                   /* Long lon=(dataSnapshot.getChildrenCount ());
                    int ii = lon.intValue();
                    Profesores[] profesores =new Profesores[ii];
                    int i=0;

                    for (DataSnapshot children:dataSnapshot.getChildren() ){
                        Log.d("errorExtraño",dataSnapshot.getKey());
                        Gson gson=new Gson();
                        Log.d("errorExtraño",gson.toJson(dataSnapshot.getValue()));
                        Profesores profesor =children.getValue(Profesores.class);
                        profesor.setKey(Integer.parseInt(children.getKey()));

                        Array.set(profesores,i, profesor);

                        i++;
                    }
                    lista = profesores;
                    ListaUsuariosAdapter adapter=new ListaUsuariosAdapter(lista,VistaPrincipalActivity.this);
                    RecyclerView recyclerView=findViewById(R.id.RecyclerViewProfesores);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(VistaPrincipalActivity.this));
                    guardarComoTexto(lista);
                    Log.d("Usuarios", dataSnapshot.getValue().toString());
                    Log.d("Usuarios2", lista.toString());
                    notificacionCambioDeProfesor();*/

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void notificacionCambioDeProfesor() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String chanelId = "notificacionProfesor";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(chanelId, "Profesor Añadidoo", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Un nuevo profesor ha sido añadido");
            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, chanelId);
        builder.setSmallIcon(R.drawable.ic_profesor_aniadido);
        builder.setContentTitle("Profesor Añadido");
        builder.setContentText("Un nuevo profesor ha sido añadido");
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationManager.notify(1, builder.build());

    }

    public Profesores[] leerArchivoTextoProfesores(String fileName) {

        Profesores[] arregloAsesores = null;

        try (FileInputStream fileInputStream = openFileInput(fileName);
             FileReader fileReader = new FileReader(fileInputStream.getFD());
             BufferedReader bufferedReader = new BufferedReader(fileReader);) {

            Gson gson = new Gson();

            String line = bufferedReader.readLine();

            arregloAsesores = gson.fromJson(line, Profesores[].class);



           /* for(Trabajo t: arregloTrabajo){
                Log.d("infoApp",t.getJobTitle());
            }*/
            return arregloAsesores;
        } catch (IOException e) {
            e.printStackTrace();
            return arregloAsesores;
        }


    }



    public void guardarComoTexto(Profesores[] arregloAsesores) {

        String fileName = "arregloAsesores";

        try (FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
             FileWriter fileWriter = new FileWriter(fileOutputStream.getFD());) {
            Gson gson = new Gson();
            String listaComoJson = gson.toJson(arregloAsesores);
            fileWriter.write(listaComoJson);
            Log.d("infoApp", "Guardado exitoso");
        } catch (IOException e) {
            Log.d("infoApp", "Error al guardar");
            e.printStackTrace();
        }
    }

//------------------------------------
    Context context = this;

    public RolUser leerArchivoTextRol() {
        String fileName="rol_idrol";
        RolUser rolUser = null;

        try (FileInputStream fileInputStream = openFileInput(fileName);
             FileReader fileReader = new FileReader(fileInputStream.getFD());
             BufferedReader bufferedReader = new BufferedReader(fileReader);) {

            Gson gson = new Gson();

            String line = bufferedReader.readLine();

            rolUser = gson.fromJson(line, RolUser.class);



           /* for(Trabajo t: arregloTrabajo){
                Log.d("infoApp",t.getJobTitle());
            }*/
            return rolUser;
        } catch (IOException e) {
            e.printStackTrace();
            return rolUser;
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (rol_idrol == 1) {
            menu.setGroupVisible(R.id.group_rol_1, true);
            menu.findItem(R.id.moreOptionsId).setVisible(true);

        } else {

        }
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case (R.id.listaCursos):
                startActivity(new Intent(this, ListaDeCursosActivity.class));
                return true;
            case (R.id.moreOptionsId):
                if (rol_idrol==1) {
                    View menuitem = findViewById(R.id.moreOptionsId);
                    PopupMenu popupMenu = new PopupMenu(this, menuitem);
                    popupMenu.getMenuInflater().inflate(R.menu.poup_menu_profe, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.cerrarCesionId:
                                    Log.d("infoapp", "cerrando sesion");

                                    FirebaseAuth.getInstance().signOut();
                                    finish();
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(new Intent(context, MainActivity.class));
                                    return true;

                                case R.id.agregarclaseID:
                                    startActivity(new Intent(context, CrearClaseGrupal.class));
                                    return true;

                                default:
                                    startActivity(new Intent(context, ListaAsesoriaPersoActivity.class));
                                    return true;

                            }
                        }
                    });
                    popupMenu.show();
                }else{
                    View menuitem = findViewById(R.id.moreOptionsId);
                    PopupMenu popupMenu = new PopupMenu(this, menuitem);
                    popupMenu.getMenuInflater().inflate(R.menu.poup_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.cerrarCesionId:
                                    Log.d("infoapp", "cerrando sesion");

                                    FirebaseAuth.getInstance().signOut();
                                    finish();
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(new Intent(context, MainActivity.class));
                                    return true;

                                default:
                                    startActivity(new Intent(context, ListaAsesoriaPersoActivity.class));
                                    return true;

                            }
                        }
                    });
                    popupMenu.show();


                }
                return true;
            case (R.id.listaAsesoriasID):
                startActivity(new Intent(context, ListaAsesoriaPersoActivity.class));
                return true;
            default:
                return true;
        }

        //


    }

}
