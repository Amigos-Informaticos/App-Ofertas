package com.example.recycler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.recycler.comunicacion.MetaRequest;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.Oferta;
import com.example.recycler.sesion.MiembroOfercompasSesion;
import com.example.recycler.sesion.SelectorFecha;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class ActualizarOferta extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatePickerDialog datePickerDialogFechaInicio;
    private DatePickerDialog datePickerDialogFechaFin;

    private EditText txtTitulo;
    private EditText txtDescripcion;
    private EditText txtPrecio;
    private EditText txtVinculo;
    private Spinner spinnerCategoria;

    private Button dpFechaInicio;
    private Button dpFechaFin;

    private Oferta oferta;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_oferta);

        spinnerCategoria = findViewById(R.id.spCategoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
        spinnerCategoria.setOnItemSelectedListener(this);
        dpFechaInicio = findViewById(R.id.dpFechaInicio);
        dpFechaFin = findViewById(R.id.dpFechaFin);

        initValues();
        llenarCampos();

    }

    private void initValues(){
        oferta = new Oferta();
        datePickerDialogFechaInicio = SelectorFecha.initDatePicker(dpFechaInicio,this);
        datePickerDialogFechaFin = SelectorFecha.initDatePicker(dpFechaFin,this);

        this.txtTitulo = findViewById(R.id.txtTitulo);
        this.txtDescripcion = findViewById(R.id.txtDescripcion);
        this.txtPrecio = findViewById(R.id.txtPrecio);
        this.txtVinculo = findViewById(R.id.txtVinculo);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void llenarCampos(){
        oferta = (Oferta) getIntent().getExtras().getSerializable("oferta");
        Log.d("OFERTA A ACTUALIZAR: ", oferta.toString());

        txtTitulo.setText(oferta.getTitulo());
        txtDescripcion.setText(oferta.getDescripcion());
        txtPrecio.setText(String.valueOf(oferta.getPrecio()));
        txtVinculo.setText(String.valueOf(oferta.getVinculo()));

        LocalDate fechaInicio = LocalDate.parse(oferta.getFechaCreacion());
        LocalDate fechaFin = LocalDate.parse(oferta.getFechaFin());

        datePickerDialogFechaInicio.updateDate(fechaInicio.getYear(), fechaInicio.getMonthValue()-1, fechaInicio.getDayOfMonth());
        datePickerDialogFechaFin.updateDate(fechaFin.getYear(), fechaFin.getMonthValue()-1, fechaFin.getDayOfMonth());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void instanciaOferta() {
        oferta.setTitulo(this.txtTitulo.getText().toString());
        oferta.setDescripcion(txtDescripcion.getText().toString());
        oferta.setPrecio(Integer.parseInt(txtPrecio.getText().toString()));
        oferta.setFechaCreacion(dpFechaInicio.getText().toString());
        oferta.setFechaFin(dpFechaFin.getText().toString());
        oferta.setVinculo(txtVinculo.getText().toString());
        oferta.setIdPublicador(7);
        //oferta.setIdPublicador(MiembroOfercompasSesion.getIdMiembro());
        oferta.setCategoria(Integer.parseInt(String.valueOf(spinnerCategoria.getSelectedItemPosition())));
    }

    public void openDatePickerInicio(View view)
    {
        datePickerDialogFechaInicio.show();
    }

    public void openDatePickerFin(View view)
    {
        datePickerDialogFechaFin.show();
    }

    public void clicActualizar(View view){
        instanciaOferta();
        try {
            actualizar();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int actualizar() throws JSONException {
        AtomicInteger respuesta = new AtomicInteger(400);
        Log.d("OfertaPublicada", this.toString());
        if (oferta.estaCompleto()) {
            JSONObject payload = oferta.obtenerJson();
            String url = MiembroOfercompasSesion.ipSever + "ofertas/"+oferta.getIdPublicacion();
            MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.PUT, url, payload,
                    response -> {
                        try {
                            respuesta.set(response.getInt("status"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error ->
                    Log.e("ERROR PUB", "PUBLICAR"));
            ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
        } else {
            respuesta.set(400);
        }
        return respuesta.get();
    }
}