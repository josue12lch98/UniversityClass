package com.example.universityclass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.universityclass.entidades.Profesores;
import com.example.universityclass.entidades.RolUser;
import com.example.universityclass.entidades.Usuario;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int rol_idrol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void validarLogin(View view) {
        List<AuthUI.IdpConfig> listaProveeedores = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.drawable.logo_uc)
                        .setTheme(R.style.AppTheme)
                        .setAvailableProviders(listaProveeedores)
                        .build(),1
        );




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Log.d("infoApp","inicio de sesión exitoso!");
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.setLanguageCode("es-419");
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser!=null){

                    final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("UniversityClass");
                    final String uid = currentUser.getUid();
                    final String name =currentUser.getDisplayName();
                    final String email=currentUser.getEmail();
                    Log.d("infoApp",databaseReference.child("usuario/"+uid).getDatabase().toString());


                    databaseReference.child("usuarios").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            if(dataSnapshot.getValue()==null){
                                Log.d("infoApp2","inicio de sesión exitoso!");
                                Usuario usuario=new Usuario();
                                usuario.setNombre(name);
                                usuario.setCorreo(email);
                                databaseReference.child("usuarios").child(uid).setValue(usuario);
                            }
                            if (dataSnapshot.getValue()!=null){

                                int inf=dataSnapshot.child("rol_idrol").getValue(Integer.class);
                                guardarComoTextoRol(new RolUser(inf));
                                if (inf ==1){

                                    MainActivity.this.finish();
                                    Log.d("este","Profesor");
                                    Intent intent = new Intent(MainActivity.this,VistaPrincipalActivity.class);



                                    startActivity(intent);
                                }else{

                                    MainActivity.this.finish();

                                    Intent intent = new Intent(MainActivity.this,VistaPrincipalActivity.class);


                                    startActivity(intent);
                                }


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Log.d("verificacion",String.valueOf(currentUser.isEmailVerified()));

                    if(!currentUser.isEmailVerified()){
                        currentUser.sendEmailVerification()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this,
                                                "Se le ha enviado un correo para validar su cuenta",
                                                Toast.LENGTH_SHORT).show();
                                        Log.d("infoApp","Envío de correo exitoso");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("infoApp","error al enviar el correo");
                                    }
                                });
                        finish();
                        startActivity(new Intent(MainActivity.this,MainActivity.class));

                    }

                }else{
                    Log.d("infoApp","Error en al obtener usuario");

                }

            }else{
                Log.d("infoApp","Inicio erroneo");
            }
        }

    }

    public void guardarComoTextoRol(RolUser rolUser) {

        String fileName = "rol_idrol";

        try (FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
             FileWriter fileWriter = new FileWriter(fileOutputStream.getFD());) {
            Gson gson = new Gson();
            String listaComoJson = gson.toJson(rolUser);
            fileWriter.write(listaComoJson);
            Log.d("infoApp", "Guardado exitoso");

        } catch (IOException e) {
            Log.d("infoApp", "Error al guardar");
            e.printStackTrace();

        }
    }



}
