package com.example.universityclass;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.time.LocalDateTime;
import java.util.Calendar;

 public  class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (getActivity() instanceof AsesoriaPersonalizada) {
                    AsesoriaPersonalizada activity = (AsesoriaPersonalizada) getActivity();
                    activity.mostrarHoraSeleccionada(hourOfDay, minute);
                }
                else if (getActivity() instanceof CrearClaseGrupal){
                    CrearClaseGrupal activity = (CrearClaseGrupal) getActivity();
                    activity.mostrarHoraSeleccionada(hourOfDay, minute);


                }            }
        }, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }


     @Override
     public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

     }
 }