package com.example.universityclass;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.universityclass.entidades.AsesoriaPersonalizadaEntity;
import com.example.universityclass.entidades.Profesores;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CrearClaseGrupal extends AppCompatActivity {
    String cursoSeleccionado;

    int dia;
    int mes;
    int anio;
    int hora;
    Profesores profesor;
    int min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_clase_grupal);


    }
    public void agregarDia(View view){
        DatePickerFragment datePickerFragment=new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(),"datepicker");
    }
    public void mostrarFechaSeleccionada(int anio, int mes, int dia) {

        TextView diaView= this.findViewById(R.id.diaID);
        String diacompleto=anio+ "/" +mes+"/"+ dia;
        diaView.setText(diacompleto);
        mes++;
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
        mes=mes +1;

        LocalDateTime as =  LocalDateTime.of(anio,mes,dia,hora,min,0,0);
        Instant instant = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            instant = as.toInstant(ZoneOffset.ofHours(-5));
        }


        Date startDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startDate = Date.from(instant);

        }

Spinner spinner= findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.cursos,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             cursoSeleccionado=parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                 cursoSeleccionado="--";


            }
        });


        Log.d("ResultadoHora",startDate.toString());
        //Guardar en firebase con el id del usuario y el del profesor
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("UniversityClass");
        AsesoriaPersonalizadaEntity asesoriaPersonalizada= new AsesoriaPersonalizadaEntity();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");


        asesoriaPersonalizada.setFecha(dtf.format(as));
        asesoriaPersonalizada.setFechaCompleta(startDate.toString());
        asesoriaPersonalizada.setNombreProfesor(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        asesoriaPersonalizada.setNombreUsuario(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        TextView textView=findViewById(R.id.idTema);
        asesoriaPersonalizada.setNombre(cursoSeleccionado);
        asesoriaPersonalizada.setDescripcion(textView.getText().toString());
        asesoriaPersonalizada.setFoto_url("https://i.ytimg.com/vi/guGY4MmRWHQ/maxresdefault.jpg");
        asesoriaPersonalizada.setIdProfesor(FirebaseAuth.getInstance().getUid());

        String keyGrupal=databaseReference.child("Profesores/listaprofesores/"+FirebaseAuth.getInstance().getUid()+"/asesoriasGrupales/listaAsesorias").push().getKey();
        asesoriaPersonalizada.setKey(keyGrupal);
        databaseReference.child("Profesores/listaprofesores/"+FirebaseAuth.getInstance().getUid()+"/asesoriasGrupales/listaAsesorias").child(keyGrupal).setValue(asesoriaPersonalizada);

        databaseReference.child("Cursos/listacursos").child(keyGrupal).setValue(asesoriaPersonalizada);


    }




}