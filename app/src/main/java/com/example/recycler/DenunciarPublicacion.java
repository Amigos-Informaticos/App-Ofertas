package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.recycler.comunicacion.MetaRequest;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.Publicacion;
import com.example.recycler.sesion.MiembroOfercompasSesion;

import org.json.JSONException;
import org.json.JSONObject;

public class DenunciarPublicacion extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView titulo;
    private EditText txtComentario;
    private Spinner motivos;
    private String motivo;
    private Button btnDenunciar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denunciar_publicacion);




        this.motivos = findViewById(R.id.spinnerMotivos);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.motivos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motivos.setAdapter(adapter);
        motivos.setOnItemSelectedListener(this);
        initViews();
    }

    private void initViews(){
        this.titulo= findViewById(R.id.txtTituloPublicacionD);
        this.btnDenunciar = findViewById(R.id.buttonDenunciar_);
        this.titulo.setText(MiembroOfercompasSesion.tituloPublicacionDenunciar);
        this.txtComentario = findViewById(R.id.txtComentarioDenuncia);


    }

    public void clicDenunciarPublicacion(View view) {
        String comentario = this.txtComentario.getText().toString();
        if(Publicacion.comentarioValido(comentario)){
            if(motivo.isEmpty() || !motivo.equals("Motivo")){
                this.btnDenunciar.setEnabled(false);
                denunciar();
            }else{
                this.mostrarMensaje("Elige el motivo de tu denuncia");
            }

        }else{
            this.mostrarMensaje("Comentario inválido, es muy pequeño");
        }
    }
    private void denunciar(){

        JSONObject object = new JSONObject();
        try {
            object.put("idDenunciante",MiembroOfercompasSesion.getIdMiembro());
            object.put("comentario",txtComentario.getText().toString());
            object.put("motivo", this.obtenerMotivo(motivo));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        String url = MiembroOfercompasSesion.ipSever+ "publicaciones/" + MiembroOfercompasSesion.idPublicacionDenunciar+ "/denuncias";
        MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mostrarMensaje("Denuncia Extitosa, solo puedes hacer una denuncia por publicacion");


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
    private void mostrarMensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.motivo = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private  int obtenerMotivo(String motivo){
        int motivoInt = 0;
        if(motivo.equals("Alcohol")){
            motivoInt = 7;
        }else if (motivo.equals("Animales")){
            motivoInt = 4;
        }else if (motivo.equals("Armas")){
            motivoInt = 2;
        }else if( motivo.equals("Drogas")){
            motivoInt = 1;
        }else if (motivo.equals("Fraude")){
            motivoInt = 5;
        }else if (motivo.equals("LinkCaido")){
            motivoInt = 10;
        }else if(motivo.equals("Ofensivo")){
            motivoInt = 8;
        }else if(motivo.equals("Pirateria")){
            motivoInt = 9;
        }else if (motivo.equals("Pornografia")){
            motivoInt = 3;
        }else {
            motivoInt = 6;
        }

        return motivoInt;
    }


}