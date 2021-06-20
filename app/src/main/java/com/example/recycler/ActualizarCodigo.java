package com.example.recycler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.recycler.comunicacion.MetaRequest;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.CodigoDescuento;
import com.example.recycler.model.Oferta;
import com.example.recycler.sesion.MiembroOfercompasSesion;
import com.example.recycler.sesion.SelectorFecha;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.time.LocalDate;

public class ActualizarCodigo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatePickerDialog datePickerDialogFechaInicio;
    private DatePickerDialog datePickerDialogFechaFin;

    private Bitmap bitmap;
    private Uri uriFoto;

    private TextView tvTituloFoto;
    private TextView txtCodigo;

    private EditText txtTitulo;
    private EditText txtDescripcion;
    private Spinner spinnerCategoria;

    private Button dpFechaInicio;
    private Button dpFechaFin;


    private CodigoDescuento codigoDescuento;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_codigo);

        spinnerCategoria = findViewById(R.id.spCategoriaCodigo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
        spinnerCategoria.setOnItemSelectedListener(this);
        dpFechaInicio = findViewById(R.id.dpFechaInicioCodigo);
        dpFechaFin = findViewById(R.id.dpFechaFinCodigo);

        initValues();
        llenarCampos();

    }

    private void initValues(){
        codigoDescuento = new CodigoDescuento();
        datePickerDialogFechaInicio = SelectorFecha.initDatePicker(dpFechaInicio,this);
        datePickerDialogFechaFin = SelectorFecha.initDatePicker(dpFechaFin,this);

        this.txtTitulo = findViewById(R.id.txtTituloCodigo);
        this.txtDescripcion = findViewById(R.id.txtDescripcionCodigo);
        this.txtCodigo = findViewById(R.id.txtCodigo);
        tvTituloFoto = findViewById(R.id.tvTituloFoto);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void llenarCampos(){
        codigoDescuento = (CodigoDescuento) getIntent().getExtras().getSerializable("codigo");
        Log.d("OFERTA A ACTUALIZAR: ", codigoDescuento.toString());

        txtTitulo.setText(codigoDescuento.getTitulo());
        txtDescripcion.setText(codigoDescuento.getDescripcion());
        txtCodigo.setText(String.valueOf(codigoDescuento.getCodigo()));

        LocalDate fechaInicio = LocalDate.parse(codigoDescuento.getFechaCreacion());
        LocalDate fechaFin = LocalDate.parse(codigoDescuento.getFechaFin());

        datePickerDialogFechaInicio.updateDate(fechaInicio.getYear(), fechaInicio.getMonthValue()-1, fechaInicio.getDayOfMonth());
        datePickerDialogFechaFin.updateDate(fechaFin.getYear(), fechaFin.getMonthValue()-1, fechaFin.getDayOfMonth());

        dpFechaInicio.setText(codigoDescuento.getFechaCreacion());
        dpFechaFin.setText(codigoDescuento.getFechaFin());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void instanciaCodigo() {
        codigoDescuento.setTitulo(this.txtTitulo.getText().toString());
        codigoDescuento.setDescripcion(txtDescripcion.getText().toString());
        codigoDescuento.setFechaCreacion(dpFechaInicio.getText().toString());
        codigoDescuento.setFechaFin(dpFechaFin.getText().toString());
        codigoDescuento.setCodigo(txtCodigo.getText().toString());
        codigoDescuento.setIdPublicador(7);
        //oferta.setIdPublicador(MiembroOfercompasSesion.getIdMiembro());
        codigoDescuento.setCategoria(Integer.parseInt(String.valueOf(spinnerCategoria.getSelectedItemPosition())));
    }

    public void openDatePickerInicio(View view)
    {
        datePickerDialogFechaInicio.show();
    }

    public void openDatePickerFin(View view)
    {
        datePickerDialogFechaFin.show();
    }

    public void actualizar(View view) {
        instanciaCodigo();
        if (codigoDescuento.estaCompleta()) {
            JSONObject payload = null;
            try {
                payload = codigoDescuento.obtenerJson();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String url = MiembroOfercompasSesion.ipSever + "codigos/" + codigoDescuento.getIdPublicacion();
            MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.PUT, url, payload,
                    response -> {
                        Toast.makeText(this, "Codigo actualizado exitosamente", Toast.LENGTH_SHORT).show();
                        this.regresarAlInicio();

                    }, error -> {
                Toast.makeText(this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
            });
            ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
        } else {
            Toast.makeText(this, "Información incorrecta", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            uriFoto = data.getData();
            String path = uriFoto.getPath();
            tvTituloFoto.setText(path);
        }
    }

    public void regresarAlInicio() {
        Intent miIntent = new Intent(this, MainActivity.class);
        startActivity(miIntent);
    }



}