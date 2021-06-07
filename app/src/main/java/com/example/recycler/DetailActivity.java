package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recycler.model.Oferta;
import com.example.recycler.sesion.MiembroOfercompasSesion;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgItemDetail;
    private TextView tvTituloDetail;
    private TextView tvDescripcionDetail;
    private EditText txtComentario;
    private Oferta oferta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle(getClass().getSimpleName());

        initViews();
        initValues();
    }

    private void initViews() {
        imgItemDetail = findViewById(R.id.imgItemDetail);
        tvTituloDetail = findViewById(R.id.tvTituloDetail);
        tvDescripcionDetail = findViewById(R.id.tvDescripcionDetail);
        txtComentario = findViewById(R.id.txtComentario);
    }

    private void initValues(){
        oferta = (Oferta) getIntent().getExtras().getSerializable("itemDetail");

        imgItemDetail.setImageResource(oferta.getImgResource());
        tvTituloDetail.setText(oferta.getTitulo());
        tvDescripcionDetail.setText(oferta.getDescripcion());
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