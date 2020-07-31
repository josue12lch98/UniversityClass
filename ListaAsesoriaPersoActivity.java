package com.example.universityclass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.PopupMenu;

import com.example.universityclass.entidades.AsesoriaPersonalizadaEntity;
import com.example.universityclass.entidades.ListaAsesoriasPersonalizadas;
import com.example.universityclass.entidades.Profesores;
import com.example.universityclass.entidades.RolUser;
import com.example.universityclass.entidades.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;

public class ListaAsesoriaPersoActivity extends AppCompatActivity {
    ListaAsesoriasPersonalizadas listaAsesoriasPersonalizadas;
    int rol_idrol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lista_asesoria_perso);
       final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UniversityClass");
        RolUser rolUser=leerArchivoTextRol();
        rol_idrol=rolUser.getRol_idrol();

        databaseReference.child("usuarios").child(FirebaseAuth.getInstance().getUid()).child("rol_idrol").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rol_idrol = dataSnapshot.getValue(Integer.class);

                if(rol_idrol==0){   databaseReference.child("usuarios").child(FirebaseAuth.getInstance().getUid()).child("solicitudes").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Long lon = (dataSnapshot.getChildrenCount());
                        int ii = lon.intValue();
                        AsesoriaPersonalizadaEntity[] profesores = new AsesoriaPersonalizadaEntity[ii];
                        int i = 0;

                        for (DataSnapshot children : dataSnapshot.getChildren()) {
                            Log.d("Usuarios", dataSnapshot.getValue().toString());
                            AsesoriaPersonalizadaEntity profesor = children.getValue(AsesoriaPersonalizadaEntity.class);
                            profesor.setRol_idrol(rol_idrol);
                            profesor.setKey(children.getKey());
                            Array.set(profesores, i, profesor);
                            i++;
                        }
                        listaAsesoriasPersonalizadas = dataSnapshot.getValue(ListaAsesoriasPersonalizadas.class);
                        ListaAsePersonalizadaAdapter adapter = new ListaAsePersonalizadaAdapter(profesores, ListaAsesoriaPersoActivity.this);
                        RecyclerView recyclerView = findViewById(R.id.RecyclerViewProfesorePerso);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ListaAsesoriaPersoActivity.this));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
                }else if(rol_idrol==1){
                    databaseReference.child("Profesores/listaprofesores").child(FirebaseAuth.getInstance().getUid()).child("asesoriasPersonalizadas").addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Long lon=(dataSnapshot.getChildrenCount ());
                            int ii = lon.intValue();
                            AsesoriaPersonalizadaEntity[] profesores =new AsesoriaPersonalizadaEntity[ii];
                            int i=0;

                            for (DataSnapshot children:dataSnapshot.getChildren() ){
                                Log.d("Usuarios", dataSnapshot.getValue().toString());
                                AsesoriaPersonalizadaEntity profesor =children.getValue(AsesoriaPersonalizadaEntity.class);
                                profesor.setRol_idrol(rol_idrol);
                                profesor.setKey(children.getKey());
                                Array.set(profesores,i, profesor);
                                i++;
                            }
                            listaAsesoriasPersonalizadas   = dataSnapshot.getValue(ListaAsesoriasPersonalizadas.class);
                            ListaAsePersonalizadaAdapter adapter=new ListaAsePersonalizadaAdapter(profesores,ListaAsesoriaPersoActivity.this);
                            RecyclerView recyclerView=findViewById(R.id.RecyclerViewProfesorePerso);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ListaAsesoriaPersoActivity.this));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    //menuuuuuuuuuuuuuuuuuuuuuuuu
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



    ///menuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu
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
                                case R.id.verInscritosID:
                                    return true;
                                case R.id.agregarclaseID:
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
            default:
                return true;
        }

        //


    }

}


