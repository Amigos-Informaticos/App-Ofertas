package com.example.recycler;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.example.recycler.comunicacion.MetaRequest;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.CodigoDescuento;
import com.example.recycler.sesion.MiembroOfercompasSesion;
import com.example.recycler.sesion.SelectorFecha;

import org.json.JSONException;
import org.json.JSONObject;

public class PublicarCodigo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatePickerDialog datePickerDialogFechaInicio;
    private DatePickerDialog datePickerDialogFechaFin;

    private EditText txtTitulo;
    private EditText txtDescripcion;
    private EditText txtCodigo;
    private Spinner spinnerCategoria;

    private Button dpFechaInicio;
    private Button dpFechaFin;
    private CodigoDescuento codigoDescuento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_codigo);

        spinnerCategoria = findViewById(R.id.spCategoriaCodigo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
        spinnerCategoria.setOnItemSelectedListener(this);
        dpFechaInicio = findViewById(R.id.dpFechaInicioCodigo);
        dpFechaFin = findViewById(R.id.dpFechaFinCodigo);

        initValues();

    }

    private void initValues() {
        codigoDescuento = new CodigoDescuento();
        datePickerDialogFechaInicio = SelectorFecha.initDatePicker(dpFechaInicio, this);
        datePickerDialogFechaFin = SelectorFecha.initDatePicker(dpFechaFin, this);

        this.txtTitulo = findViewById(R.id.txtTituloCodigo);
        this.txtDescripcion = findViewById(R.id.txtDescripcionCodigo);
        this.txtCodigo = findViewById(R.id.txtCodigo);
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
        codigoDescuento.setCodigo(txtCodigo.getText().toString());
        codigoDescuento.setFechaCreacion(dpFechaInicio.getText().toString());
        codigoDescuento.setFechaFin(dpFechaFin.getText().toString());
        codigoDescuento.setIdPublicador(MiembroOfercompasSesion.getIdMiembro());
        codigoDescuento.setCategoria(Integer.parseInt(String.valueOf(spinnerCategoria.getSelectedItemPosition())));
    }

    public void openDatePickerInicio(View view) {
        datePickerDialogFechaInicio.show();
    }

    public void openDatePickerFin(View view) {
        datePickerDialogFechaFin.show();
    }

    public void publicar(View view) {
        instanciaCodigo();
        Log.d("Codigo", this.toString());
        if (codigoDescuento.estaCompleta()) {
            JSONObject payload = null;
            try {
                payload = codigoDescuento.obtenerJson();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String url = MiembroOfercompasSesion.ipSever + "codigos";
            MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.POST, url, payload,
                    response -> {
                        Toast.makeText(this, "Código registrado exitosamente", Toast.LENGTH_SHORT).show();
                    }, error -> mostrarMensaje("Error servidor"));
            ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
        } else {
            Toast.makeText(this, "Información incorrecta", Toast.LENGTH_SHORT).show();
        }
    }

    public void mostrarMensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}