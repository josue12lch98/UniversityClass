package com.example.universityclass;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragmentGroup extends DialogFragment implements DatePickerDialog.OnDateSetListener {

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
                CrearClaseGrupal activity = (CrearClaseGrupal) getActivity();
                activity.mostrarFechaSeleccionada(year, month, dayOfMonth);
            }
        }, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
