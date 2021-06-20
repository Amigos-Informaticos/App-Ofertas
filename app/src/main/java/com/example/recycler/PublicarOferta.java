package com.example.recycler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.example.recycler.comunicacion.MetaRequest;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.Oferta;
import com.example.recycler.sesion.MiembroOfercompasSesion;
import com.example.recycler.sesion.SelectorFecha;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class PublicarOferta extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatePickerDialog datePickerDialogFechaInicio;
    private DatePickerDialog datePickerDialogFechaFin;

    private EditText txtTitulo;
    private EditText txtDescripcion;
    private EditText txtPrecio;
    private EditText txtVinculo;
    private Spinner spinnerCategoria;

    private Button dpFechaInicio;
    private Button dpFechaFin;
    private Button btnBuscarImagen;

    private TextView tvTituloFoto;

    private Oferta oferta;
    private File foto;

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
        btnBuscarImagen = findViewById(R.id.btnBuscarImagen);

        initValues();

    }

    private void initValues() {
        oferta = new Oferta();
        datePickerDialogFechaInicio = SelectorFecha.initDatePicker(dpFechaInicio, this);
        datePickerDialogFechaFin = SelectorFecha.initDatePicker(dpFechaFin, this);

        this.txtTitulo = findViewById(R.id.txtTitulo);
        this.txtDescripcion = findViewById(R.id.txtDescripcion);
        this.txtPrecio = findViewById(R.id.txtPrecio);
        this.txtVinculo = findViewById(R.id.txtVinculo);
        this.tvTituloFoto = findViewById(R.id.tvTituloFoto);

        botonBuscarImagen();
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

    public void openDatePickerInicio(View view) {
        datePickerDialogFechaInicio.show();
    }

    public void openDatePickerFin(View view) {
        datePickerDialogFechaFin.show();
    }

    public void publicar(View view) {
        instanciaOferta();
        Log.d("Codigo", this.toString());
        if (oferta.estaCompleta()) {
            JSONObject payload = null;
            try {
                payload = oferta.obtenerJson();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String url = MiembroOfercompasSesion.ipSever + "codigos";
            MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.POST, url, payload,
                    response -> {
                        Toast.makeText(this, "Oferta registrado exitosamente", Toast.LENGTH_SHORT).show();
                    }, error -> Toast.makeText(this, "Código registrado exitosamente", Toast.LENGTH_SHORT).show());
            ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
        } else {
            Toast.makeText(this, "Información incorrecta", Toast.LENGTH_SHORT).show();
        }
    }

    public void botonBuscarImagen() {
        btnBuscarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            String path = targetUri.getPath();
            this.foto = new File(path);
            tvTituloFoto.setText(path);
        }
    }
}