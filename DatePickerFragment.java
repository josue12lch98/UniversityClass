package com.example.universityclass;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.universityclass.entidades.AsesoriaGrupal;
import com.example.universityclass.entidades.Cursos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c =Calendar.getInstance();
        int year =c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);





        return new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (getActivity() instanceof AsesoriaPersonalizada){
                AsesoriaPersonalizada activity = (AsesoriaPersonalizada) getActivity();
                activity.mostrarFechaSeleccionada(year, month, dayOfMonth);}
                else if (getActivity() instanceof CrearClaseGrupal){

                    CrearClaseGrupal activity = (CrearClaseGrupal) getActivity();
                    activity.mostrarFechaSeleccionada(year, month, dayOfMonth);}

                }

        }, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
