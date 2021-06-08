package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recycler.model.Oferta;
import com.example.recycler.sesion.MiembroOfercompasSesion;

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

    private Oferta oferta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_oferta);
        setTitle(getClass().getSimpleName());

        initViews();
        initValues();
    }

    private void initViews() {
        imgItemDetail = findViewById(R.id.imgItemDetail);
        tvTituloDetail = findViewById(R.id.tvTituloDetail);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        txtComentario = findViewById(R.id.txtComentario);
        tvPrecio = findViewById(R.id.tvPrecio);
        tvPuntuacion = findViewById(R.id.tvPuntuacion);
        btnLike = findViewById(R.id.btnLike);
        btnDisike = findViewById(R.id.btnDislike);
        btnIrAOferta = findViewById(R.id.btnIrAOferta);
    }

    private void initValues(){
        oferta = (Oferta) getIntent().getExtras().getSerializable("itemDetail");

        imgItemDetail.setImageResource(oferta.getImgResource());
        tvTituloDetail.setText(oferta.getTitulo());
        tvDescripcion.setText(oferta.getDescripcion());
    }

    public void likeClik(View view) {
        String comentario = "El id de la oferta es:" + oferta.getIdPublicacion() + "La sesion del usuario es:" + MiembroOfercompasSesion.getNickname();
        txtComentario.setText(comentario);
    }

    public void comentarClic(View view) {

    }

    public void dislikeClic(View view) {
    }
}