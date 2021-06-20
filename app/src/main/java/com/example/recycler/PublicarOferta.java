package com.example.recycler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.recycler.comunicacion.DataPart;
import com.example.recycler.comunicacion.MetaRequest;
import com.example.recycler.comunicacion.VolleyMultipartRequest;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.Oferta;
import com.example.recycler.sesion.MiembroOfercompasSesion;
import com.example.recycler.sesion.SelectorFecha;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PublicarOferta extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatePickerDialog datePickerDialogFechaInicio;
    private DatePickerDialog datePickerDialogFechaFin;

    private Bitmap bitmap;
    private Uri uriFoto;

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

    /*
    public void publicar(View view) {
        btnBuscarImagen.setEnabled(false);
        instanciaOferta();
        if (oferta.estaCompleta()) {
            JSONObject payload = null;
            try {
                payload = oferta.obtenerJson();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String url = MiembroOfercompasSesion.ipSever + "ofertas";
            MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.POST, url, payload,
                    response -> {
                        Toast.makeText(this, "Oferta registrada exitosamente", Toast.LENGTH_SHORT).show();
                        this.regresarAlInicio();
                    }, error -> {
                Toast.makeText(this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                btnBuscarImagen.setEnabled(false);
            });
            ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
        } else {
            Toast.makeText(this, "Información incorrecta", Toast.LENGTH_SHORT).show();
        }
    }

     */



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
            uriFoto = data.getData();
            String path = uriFoto.getPath();
            this.foto = new File(path);
            tvTituloFoto.setText(path);
        }
    }

    public void regresarAlInicio() {
        Intent miIntent = new Intent(this, MainActivity.class);
        startActivity(miIntent);
    }

    public void publicarFoto(View view) {
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriFoto);
            uploadBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }











    private void uploadBitmap(final Bitmap bitmap) {
        String url = MiembroOfercompasSesion.ipSever + "publicaciones/" + 218 + "/multimedia";

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError",""+error.getMessage());
                    }
                }) {


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("archivo", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}