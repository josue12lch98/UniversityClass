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

import com.example.universityclass.entidades.Cursos;
import com.example.universityclass.entidades.Profesores;
import com.example.universityclass.entidades.RolUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class ClasesGrupalesActivity extends AppCompatActivity {
    Cursos[] arrgegloCursos;
    Cursos[] lista;
    Profesores profesor;
    int rol_idrol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clases_grupales);
         Intent intent=getIntent();
        RolUser rolUser=leerArchivoTextRol();
        rol_idrol=rolUser.getRol_idrol();
         profesor=(Profesores) intent.getSerializableExtra("profesor");


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("UniversityClass");


            databaseReference.child("Profesores/listaprofesores/"+profesor.getIdusuario()+"/asesoriasGrupales").addChildEventListener(new ChildEventListener() {


                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Long lon=(dataSnapshot.getChildrenCount ());
                    int ii = lon.intValue();
                    Cursos[] cursos =new Cursos[ii];
                    int i=0;

                    for (DataSnapshot children:dataSnapshot.getChildren() ){
                        Log.d("Cursos", dataSnapshot.getValue().toString());
                        Cursos curso =children.getValue(Cursos.class);

                        Array.set(cursos,i, curso);

                        i++;
                    }
                    lista = cursos;
                    ListaCursosAdapter adapter=new ListaCursosAdapter(lista,ClasesGrupalesActivity.this);
                    RecyclerView recyclerView=findViewById(R.id.RViewCursos);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ClasesGrupalesActivity.this));
                    guardarComoTextoCursos(lista);
                    Log.d("Usuarios", dataSnapshot.getValue().toString());
                    Log.d("Usuarios2", lista.toString());

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Long lon=(dataSnapshot.getChildrenCount ());
                    int ii = lon.intValue();
                    Cursos[] cursos =new Cursos[ii];
                    int i=0;

                    for (DataSnapshot children:dataSnapshot.getChildren() ){
                        Log.d("errorExtraño",dataSnapshot.getKey());
                        Gson gson=new Gson();
                        Log.d("errorExtraño",gson.toJson(dataSnapshot.getValue()));
                        Cursos curso =children.getValue(Cursos.class);

                        Array.set(cursos,i, curso);

                        i++;
                    }
                    lista = cursos;
                    ListaCursosAdapter adapter=new ListaCursosAdapter(lista,ClasesGrupalesActivity.this);
                    RecyclerView recyclerView=findViewById(R.id.RViewCursos);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ClasesGrupalesActivity.this));
                    guardarComoTextoCursos(lista);
                    Log.d("Usuarios", dataSnapshot.getValue().toString());
                    Log.d("Usuarios2", lista.toString());
                    notificacionCambioDeCurso();

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
        /*else{
            Log.e("Arreglo Asesores",arrgegloCursos.toString());
            ListaCursosAdapter adapter=new ListaCursosAdapter(arrgegloCursos,ClasesGrupalesActivity.this);
            RecyclerView recyclerView=findViewById(R.id.RViewCursos);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(ClasesGrupalesActivity.this));

            databaseReference.child("Profesores").addChildEventListener(new ChildEventListener() {


                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Long lon=(dataSnapshot.getChildrenCount ());
                    int ii = lon.intValue();
                    Cursos[] cursos =new Cursos[ii];
                    int i=0;

                    for (DataSnapshot children:dataSnapshot.getChildren() ){
                        Log.d("Cursos", dataSnapshot.getValue().toString());
                        Cursos curso =children.getValue(Cursos.class);

                        Array.set(cursos,i, curso);

                        i++;
                    }
                    lista = cursos;
                    ListaCursosAdapter adapter=new ListaCursosAdapter(lista,ClasesGrupalesActivity.this);
                    RecyclerView recyclerView=findViewById(R.id.RViewCursos);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ClasesGrupalesActivity.this));
                    guardarComoTextoCursos(lista);
                    Log.d("Usuarios", dataSnapshot.getValue().toString());
                    Log.d("Usuarios2", lista.toString());

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Long lon=(dataSnapshot.getChildrenCount ());
                    int ii = lon.intValue();
                    Cursos[] cursos =new Cursos[ii];
                    int i=0;

                    for (DataSnapshot children:dataSnapshot.getChildren() ){
                        Log.d("errorExtraño",dataSnapshot.getKey());
                        Gson gson=new Gson();
                        Log.d("errorExtraño",gson.toJson(dataSnapshot.getValue()));
                        Cursos curso =children.getValue(Cursos.class);

                        Array.set(cursos,i, curso);

                        i++;
                    }
                    lista = cursos;
                    ListaCursosAdapter adapter=new ListaCursosAdapter(lista,ClasesGrupalesActivity.this);
                    RecyclerView recyclerView=findViewById(R.id.RViewCursos);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ClasesGrupalesActivity.this));
                    guardarComoTextoCursos(lista);
                    Log.d("Usuarios", dataSnapshot.getValue().toString());
                    Log.d("Usuarios2", lista.toString());
                    notificacionCambioDeCurso();

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


        }*/
    }
    public Cursos[] leerArchivoTextoCursos(String fileName) {

        Cursos[] arregloCursos=null;

        try (FileInputStream fileInputStream = openFileInput(fileName);
             FileReader fileReader = new FileReader(fileInputStream.getFD());
             BufferedReader bufferedReader = new BufferedReader(fileReader);) {

            Gson gson = new Gson();

            String line = bufferedReader.readLine();

            arregloCursos = gson.fromJson(line, Cursos[].class);



           /* for(Trabajo t: arregloTrabajo){
                Log.d("infoApp",t.getJobTitle());
            }*/
            return arregloCursos;
        } catch (IOException e) {
            e.printStackTrace();
            return arregloCursos;
        }


    }

    public void guardarComoTextoCursos(Cursos[] arregloCursos) {

        String fileName = "arregloCursos";

        try (FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
             FileWriter fileWriter = new FileWriter(fileOutputStream.getFD());) {
            Gson gson = new Gson();
            String listaComoJson = gson.toJson(arregloCursos);
            fileWriter.write(listaComoJson);
            Log.d("infoApp", "Guardado exitoso");
        } catch (IOException e) {
            Log.d("infoApp", "Error al guardar");
            e.printStackTrace();
        }};
    public void notificacionCambioDeCurso(){
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String chanelId="notificacionProfesor";
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O ){
            NotificationChannel notificationChannel= new NotificationChannel(chanelId,"Profesor Añadidoo",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Un nuevo profesor ha sido añadido");
            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this,chanelId);
        builder.setSmallIcon(R.drawable.ic_nuevo_curso);
        builder.setContentTitle("Curso cambiado");
        builder.setContentText("Un nuevo profesor ha sido añadido");
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationManager.notify(1,builder.build());

    }


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






