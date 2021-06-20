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
import com.example.recycler.comunicacion.MetaStringRequest;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.CodigoDescuento;
import com.example.recycler.sesion.MiembroOfercompasSesion;

public class DetailCodigo extends AppCompatActivity {
    private ImageView imgItemDetail;
    private TextView tvTituloDetail;
    private TextView tvDescripcion;
    private EditText txtComentario;
    private TextView tvCodigo;
    private TextView tvPuntuacion;
    private ImageButton btnLike;
    private ImageButton btnDisike;
    private Button btnIrAOferta;
    private Button btnComenta;
    private Button btnIrDenuncia;
    private Button btnEliminar;
    private Button btnActualizar;

    private CodigoDescuento codigoDescuento;
    public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_codigo);
        setTitle(getClass().getSimpleName());

        initViews();
        initValues();
        funcionEliminar();
    }

    private void initViews() {
        imgItemDetail = findViewById(R.id.imgItemDetail);
        tvTituloDetail = findViewById(R.id.tvTituloDetail);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        txtComentario = findViewById(R.id.txtComentario);
        tvCodigo = findViewById(R.id.tvCodigo);
        tvPuntuacion = findViewById(R.id.tvPuntuacion);
        btnLike = findViewById(R.id.btnLike);
        btnDisike = findViewById(R.id.btnDislike);
        btnIrAOferta = findViewById(R.id.btnIrAOferta);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnActualizar = findViewById(R.id.btnActualizar);
    }

    private void initValues() {
        codigoDescuento = (CodigoDescuento) getIntent().getExtras().getSerializable("itemDetail");
        Log.d("OFERTA A VER: ", codigoDescuento.toString());
        tvTituloDetail.setText(codigoDescuento.getTitulo());
        tvDescripcion.setText(codigoDescuento.getDescripcion());
        tvCodigo.setText(String.valueOf(codigoDescuento.getCodigo()));
        tvPuntuacion.setText(String.valueOf(codigoDescuento.getPuntuacion()));
    }

    public void likeClik(View view) {
        String comentario = "El id de la oferta es:" + codigoDescuento.getIdPublicacion() + "La sesion del usuario es:" + MiembroOfercompasSesion.getNickname();
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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailCodigo.this);
                alertDialog.setMessage("¿Está seguro que desea eliminar este Codigo?").setCancelable(true);
                alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarCodigo();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alerta = alertDialog.create();
                alerta.setTitle("Eliminar Codigo");
                alerta.show();

            }
        });
    }

    public void eliminarCodigo(){
        String url = MiembroOfercompasSesion.ipSever + "publicaciones/" + codigoDescuento.getIdPublicacion();
        MetaStringRequest stringRequest = new MetaStringRequest(Request.Method.DELETE, url,
                response -> {
                    regresarInicioCodigosDescuento();
                }, error -> {
            if (error.networkResponse != null){
                Log.d("GG","HI");
            }
        }
        );
        ApplicationController.getInstance().addToRequestQueue(stringRequest);
    }

    public void actualizar(View view) {
        Intent intent = new Intent(this, ActualizarCodigo.class);
        intent.putExtra("codigo", codigoDescuento);
        startActivity(intent);
    }

    public void regresarInicioCodigosDescuento() {
        Intent intent = new Intent(context, InicioCodigosDescuento.class);
        startActivity(intent);
    }
}