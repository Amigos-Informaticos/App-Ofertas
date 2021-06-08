package com.example.recycler.sesion;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class SelectorFecha
{
    private DatePickerDialog datePickerDialog;
    private Button dateButton;


    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    public static DatePickerDialog initDatePicker(Button dateButton, Context context)
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, style, dateSetListener, year, month, day);
        return datePickerDialog;
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private static String makeDateString(int day, int month, int year)
    {
        return year + "-" + month + "-" + day;
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}