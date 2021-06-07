package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.recycler.model.MiembroOfercompas;
import com.example.recycler.sesion.MiembroOfercompasSesion;

public class Perfil extends AppCompatActivity {
    private TextView txtEmail;
    private TextView txtNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        initViews();
    }

    public void initViews(){
        txtNickname = findViewById(R.id.txtNickName);
        txtEmail = findViewById(R.id.txtCorreoElectronico);
        txtNickname.setText(MiembroOfercompasSesion.getNickname());
        txtEmail.setText(MiembroOfercompasSesion.getEmail());
    }

    public void clicActualizar(View view) {
        Intent miIntent = new Intent(this, ActualizarPerfil.class);
        startActivity(miIntent);
    }
}