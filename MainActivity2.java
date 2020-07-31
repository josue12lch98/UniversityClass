package com.example.universityclass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

    public void agregarFecha(View view){
DatePickerFragment datePickerFragment=new DatePickerFragment();
datePickerFragment.show(getSupportFragmentManager(),"datepicker");


    }
    public void respuestaDatePicker(int year,int month , int day){



    }
}