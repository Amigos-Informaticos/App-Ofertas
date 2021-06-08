package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.recycler.model.Oferta;
import com.example.recycler.sesion.SelectorFecha;

import java.util.Calendar;

public class PublicarOferta extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatePickerDialog datePickerDialogFechaInicio;
    private DatePickerDialog datePickerDialogFechaFin;

    private EditText txtTitulo;
    private EditText txtDescripcion;
    private EditText txtPrecio;
    private EditText txtVinculo;
    private EditText txtDiaInicio;
    private EditText txtMesInicio;
    private EditText txtAnioInicio;
    private EditText txtDiaFin;
    private EditText txtMesFin;
    private EditText txtAnioFin;
    private Spinner spinner;

    private Button dpFechaInicio;
    private Button dpFechaFin;



    private Oferta oferta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_oferta);

        spinner = findViewById(R.id.spCategoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        dpFechaInicio = findViewById(R.id.dpFechaInicio);
        dpFechaFin = findViewById(R.id.dpFechaFin);

        initValues();

    }

    private void initValues(){
        datePickerDialogFechaInicio = SelectorFecha.initDatePicker(dpFechaInicio,this);
        datePickerDialogFechaFin = SelectorFecha.initDatePicker(dpFechaFin,this);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
/*
    public void instanciaOferta() {
        oferta.setTitulo(this.txtTitulo.getText().toString());
        oferta.setDescripcion(txtDescripcion.getText().toString());
        oferta.setPrecio(txtPrecio.getText());
        oferta.setFechaCreacion(String.valueOf(fechaCreacion.getValue()));
        oferta.setFechaFin(String.valueOf(fechaFin.getValue()));
        oferta.setVinculo(txtVinculo.getText());
        oferta.setCategoriaCmbBox((String) cmbCategoria.getValue());
        System.out.println(oferta.toString());
    }

 */

    public void construirFechaInicio(){

    }

    public void openDatePickerInicio(View view)
    {
        datePickerDialogFechaInicio.show();
    }

    public void openDatePickerFin(View view)
    {
        datePickerDialogFechaFin.show();
    }
}