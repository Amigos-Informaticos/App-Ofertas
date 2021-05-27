package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recycler.model.Oferta;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgItemDetail;
    private TextView tvTituloDetail;
    private TextView tvDescripcionDetail;
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
    }

    private void initValues(){
        oferta = (Oferta) getIntent().getExtras().getSerializable("itemDetail");

        imgItemDetail.setImageResource(oferta.getImgResource());
        tvTituloDetail.setText(oferta.getTitulo());
        tvDescripcionDetail.setText(oferta.getDescripcion());
    }

    public void likeClik(View view) {
    }
}