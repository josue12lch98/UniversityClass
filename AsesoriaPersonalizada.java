package com.example.universityclass;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.universityclass.entidades.AsesoriaPersonalizadaEntity;
import com.example.universityclass.entidades.Profesores;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AsesoriaPersonalizada extends AppCompatActivity {
int dia;
int mes;
int anio;
int hora;
    Profesores profesor;
    int min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asesoria_personailzada_activity);
        Intent intent=getIntent();
        profesor =(Profesores) intent.getSerializableExtra("profesor");
    }
    public void agregarDia(View view){
        DatePickerFragment datePickerFragment=new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(),"datepicker");
    }
    public void mostrarFechaSeleccionada(int anio, int mes, int dia) {

        TextView diaView= this.findViewById(R.id.diaID);
       String diacompleto=anio+ "/" +mes+"/"+ dia;
        diaView.setText(diacompleto);
        this.anio=anio;
        this.mes=mes;
        this.dia=dia;
    }

    public void agregarHora(View view){
        TimePickerFragment timePickerFragment=new TimePickerFragment();
        timePickerFragment.show(getSupportFragmentManager(),"timepicker");
    }
    public void mostrarHoraSeleccionada(int hora,int min) {

        TextView horaView= this.findViewById(R.id.horaID);
        String horaCompleta=hora+ ":" +min;
        horaView.setText(horaCompleta);
        this.hora=hora;
        this.min=min;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void guardarAsesoria(View view){

        LocalDateTime as =  LocalDateTime.of(anio,mes,dia,hora,min,0,0);
        Instant instant = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            instant = as.toInstant(ZoneOffset.ofHours(-5));
        }


        Date startDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startDate = Date.from(instant);

        }
        Log.d("ResultadoHora",startDate.toString());
        //Guardar en firebase con el id del usuario y el del profesor
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("UniversityClass");
        AsesoriaPersonalizadaEntity asesoriaPersonalizada= new AsesoriaPersonalizadaEntity();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");


        asesoriaPersonalizada.setFecha(dtf.format(as));
        asesoriaPersonalizada.setFechaCompleta(startDate.toString());
        asesoriaPersonalizada.setNombreProfesor(profesor.getNombre() +" "+profesor.getApellido());
        asesoriaPersonalizada.setIdUsuario(FirebaseAuth.getInstance().getUid());
        asesoriaPersonalizada.setNombreUsuario(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        TextView textView=findViewById(R.id.idTema);
        asesoriaPersonalizada.setDescripcion(textView.getText().toString());
        asesoriaPersonalizada.setIdProfesor(String.valueOf(profesor.getKey()));
        String key=databaseReference.child("Profesores/listaprofesores/"+profesor.getKey()+"/asesoriasPersonalizadas").push().getKey();

        databaseReference.child("Profesores/listaprofesores/"+profesor.getKey()+"/asesoriasPersonalizadas").child(key).setValue(asesoriaPersonalizada);
        databaseReference.child("usuarios/"+FirebaseAuth.getInstance().getUid()+"/solicitudes").child(key).setValue(asesoriaPersonalizada).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AsesoriaPersonalizada.this);
                alertDialog.setTitle("Asesoría registrada exitosamente");
                alertDialog.setMessage("Puede hacer otra consulta de disponibilidad al profesor o volver al menu principal");
                alertDialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                alertDialog.show();
            }
        });

    }




}