package com.example.recycler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.recycler.comunicacion.MetaRequest;
import com.example.recycler.comunicacion.MetaStringRequest;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.Oferta;
import com.example.recycler.sesion.MiembroOfercompasSesion;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgItemDetail;
    private TextView tvTituloDetail;
    private TextView tvDescripcion;
    private EditText txtComentario;
    private TextView tvPrecio;
    private TextView tvPuntuacion;
    private ImageButton btnLike;
    private ImageButton btnDisike;
    private Button btnIrAOferta;
    private Button btnComenta;
    private Button btnIrDenuncia;
    private Button btnEliminar;
    private Button btnActualizar;

    private Oferta oferta;
    public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_oferta);
        setTitle(getClass().getSimpleName());

        initViews();
        initValues();
        funcionEliminar();
    }

    private void initViews() {
        imgItemDetail = findViewById(R.id.imgItemDetail);
        Picasso.get().load("http://192.168.56.1:5000/publicaciones/217/imagenes").into(imgItemDetail);

        tvTituloDetail = findViewById(R.id.tvTituloDetail);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        txtComentario = findViewById(R.id.txtComentario);
        tvPrecio = findViewById(R.id.tvPrecio);
        tvPuntuacion = findViewById(R.id.tvPuntuacion);
        btnLike = findViewById(R.id.btnLike);
        btnDisike = findViewById(R.id.btnDislike);
        btnIrAOferta = findViewById(R.id.btnIrAOferta);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnActualizar = findViewById(R.id.btnActualizar);
    }

    private void initValues() {
        oferta = (Oferta) getIntent().getExtras().getSerializable("itemDetail");
        Log.d("OFERTA A VER: ", oferta.toString());

        //imgItemDetail.setImageResource(Integer.parseInt("http://192.168.56.1:5000/publicaciones/217/imagenes"));


        tvTituloDetail.setText(oferta.getTitulo());
        tvDescripcion.setText(oferta.getDescripcion());
        tvPrecio.setText(String.valueOf(oferta.getPrecio()));
        tvPuntuacion.setText(String.valueOf(oferta.getPuntuacion()));
    }

    public void likeClik(View view) {
        String comentario = "El id de la oferta es:" + oferta.getIdPublicacion() + "La sesion del usuario es:" + MiembroOfercompasSesion.getNickname();
        txtComentario.setText(comentario);
    }

    public void comentarClic(View view) {

    }

    public void dislikeClic(View view) {
    }

    public void funcionEliminar() {
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailActivity.this);
                alertDialog.setMessage("¿Está seguro que desea eliminar esta Oferta?").setCancelable(true);
                alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarOferta();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alerta = alertDialog.create();
                alerta.setTitle("Eliminar Oferta");
                alerta.show();

            }
        });
    }

    public void eliminarOferta(){
        String url = MiembroOfercompasSesion.ipSever + "ofertas/" + oferta.getIdPublicacion();
        MetaStringRequest stringRequest = new MetaStringRequest(Request.Method.DELETE, url,
                response -> {
                    regresarMainActivity();
                }, error -> {
            if (error.networkResponse != null){
                Log.d("GG","WEBOS");
            }
        }
        );
        ApplicationController.getInstance().addToRequestQueue(stringRequest);
    }

    public void actualizar(View view) {
        Intent intent = new Intent(this, ActualizarOferta.class);
        intent.putExtra("oferta", oferta);
        startActivity(intent);
    }

    public void regresarMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }


}