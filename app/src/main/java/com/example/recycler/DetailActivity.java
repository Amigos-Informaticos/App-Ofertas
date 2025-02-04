package com.example.recycler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.recycler.comunicacion.MetaRequest;
import com.example.recycler.comunicacion.MetaStringRequest;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.Oferta;
import com.example.recycler.model.Publicacion;
import com.example.recycler.sesion.MiembroOfercompasSesion;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgItemDetail;
    private TextView tvTituloDetail;
    private TextView tvDescripcion;
    private EditText txtComentario;
    private TextView tvPrecio;
    private TextView tvPuntuacion;
    private Button btnLike;
    private Button btnDisike;
    private Button btnIrAOferta;
    private Button btnComenta;
    private Button btnDenunciar;
    private Button btnEliminar;
    private Button btnActualizar;
    private TextView txtComentarios;


    private Oferta oferta;
    public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_oferta);
        setTitle(getClass().getSimpleName());

        initViews();
        initValues();
        obtenerInteraccion();
        validarUsuario();
        obtenerComentarios();
        funcionEliminar();
    }

    private void initViews() {
        imgItemDetail = findViewById(R.id.imgItemDetail);
        tvTituloDetail = findViewById(R.id.tvTituloDetail);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        txtComentario = findViewById(R.id.txtComentario);
        tvPrecio = findViewById(R.id.tvPrecio);
        tvPuntuacion = findViewById(R.id.tvPuntuacion);
        btnLike = findViewById(R.id.buttonLike);
        btnDisike = findViewById(R.id.Dislike);
        btnIrAOferta = findViewById(R.id.btnIrAOferta);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnDenunciar = findViewById(R.id.btnDenuncia);
        txtComentarios = findViewById(R.id.tvComentarios);
    }

    private void initValues() {
        oferta = (Oferta) getIntent().getExtras().getSerializable("itemDetail");
        Log.d("OFERTA A VER: ", oferta.toString());
        tvTituloDetail.setText(oferta.getTitulo());
        tvDescripcion.setText(oferta.getDescripcion());
        tvPrecio.setText("$" + String.valueOf(oferta.getPrecio()));
        tvPuntuacion.setText(String.valueOf(oferta.getPuntuacion()));
        int key = R.drawable.vegeta_blue;
        Picasso.with(this).invalidate(oferta.getURLFoto());
        Picasso.with(context).load(oferta.getURLFoto()).networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE).into(imgItemDetail);
       // Picasso.with(this).load(oferta.getURLFoto()).into(imgItemDetail);
    }

    public void likeClik(View view) {
        String comentario = "El id de la oferta es:" + oferta.getIdPublicacion() + "La sesion del usuario es:" + MiembroOfercompasSesion.getNickname();
        txtComentario.setText(comentario);
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
        String url = MiembroOfercompasSesion.ipSever + "publicaciones/" + oferta.getIdPublicacion();
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

    public void comentar(){
        if(Publicacion.comentarioValido(this.txtComentario.getText().toString())){
            JSONObject object = new JSONObject();
            try {
                object.put("contenido",this.txtComentario.getText().toString());
                object.put("idMiembro",MiembroOfercompasSesion.getIdMiembro());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Enter the correct url for your api service site
            String url = MiembroOfercompasSesion.ipSever+ "publicaciones/" + oferta.getIdPublicacion()+ "/comentarios";
            MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.POST, url, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            mostrarMensaje("Comentario registrado");
                            txtComentario.setText("");
                            obtenerComentarios();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse!= null){
                        mostrarMensaje("Error al enviar comentario al servidor");
                    }else{
                        mostrarMensaje("Error al enviar comentario al servidor");
                    }


                }

            });
            ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);

        }else{
            mostrarMensaje("Comentario inválido");
        }
    }


    public  void puntuar(boolean esPositiva){
        JSONObject object = new JSONObject();
        try {
            object.put("esPositiva",esPositiva);
            object.put("idMiembro",MiembroOfercompasSesion.getIdMiembro());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        String url = MiembroOfercompasSesion.ipSever+ "publicaciones/" + oferta.getIdPublicacion()+ "/puntuaciones";
        MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String puntuacionStr = tvPuntuacion.getText().toString();
                        int puntuacion = Integer.parseInt(puntuacionStr);
                        mostrarMensaje("Like registrado");
                        if(esPositiva){
                            if(esPositiva){
                                puntuacion = puntuacion + 1;
                            }else{
                                puntuacion = puntuacion - 1;
                            }
                        }
                        tvPuntuacion.setText(puntuacion);
                        obtenerInteraccion();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                obtenerInteraccion();

            }

        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
    }


    public void clicIrOferta(View view) {
        try{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(oferta.getVinculo()));
            startActivity(browserIntent);
        }catch (Exception e){
            mostrarMensaje("LINK CAÍDO");
        }

    }

    public void clicComentar(View view) {
        comentar();
    }

    public void clicDenunciar(View view) {
        MiembroOfercompasSesion.tituloPublicacionDenunciar = oferta.getTitulo();
        MiembroOfercompasSesion.idPublicacionDenunciar = oferta.getIdPublicacion();
        Intent miIntent = new Intent(this, DenunciarPublicacion.class);
        startActivity(miIntent);
    }

    public void clicEliminar(View view) {
    }


    private void mostrarMensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void clicDislike_(View view) {
        int puntuacion = oferta.getPuntuacion() - 1;
        this.tvPuntuacion.setText(puntuacion + " ");
        this.puntuar(false);

    }

    public void clicLike_(View view) {
        int puntuacion = oferta.getPuntuacion() + 1;
        this.tvPuntuacion.setText(puntuacion + " ");
        this.puntuar(true);

    }

    public  void obtenerInteraccion(){
        String url = MiembroOfercompasSesion.ipSever + "publicaciones/" +oferta.getIdPublicacion() + "/interaccion";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean puntuada = jsonObject.getBoolean("puntuada");
                            boolean denunciada = jsonObject.getBoolean("denunciada");

                            btnDenunciar.setEnabled(!denunciada);
                            btnLike.setEnabled(!puntuada);
                            btnDisike.setEnabled(!puntuada);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("idMiembro", String.valueOf(MiembroOfercompasSesion.getIdMiembro()));

                return params;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(stringRequest);

    }
    private void validarUsuario(){
        if(oferta.getIdPublicador() != MiembroOfercompasSesion.getIdMiembro()){
            if(MiembroOfercompasSesion.getTipoMiembro()!=2){
                this.btnActualizar.setVisibility(View.GONE);
                this.btnEliminar.setVisibility(View.GONE);
            }else{
                this.btnActualizar.setVisibility(View.GONE);
            }
        }
    }
    private void obtenerComentarios(){
        String url = MiembroOfercompasSesion.ipSever + "publicaciones/" +oferta.getIdPublicacion() + "/comentarios";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            mostrarMensaje("obteniendoComentarios");
                            JSONArray jsonArray = new JSONArray(response);
                            obtenerComentariosJson(jsonArray);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("idMiembro", String.valueOf(MiembroOfercompasSesion.getIdMiembro()));

                return params;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(stringRequest);
    }

    private String obtenerComentariosJson(JSONArray array){
        StringBuilder comentarios = new StringBuilder();
        for(int i=0;i<array.length();i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = array.getJSONObject(i);
                comentarios.append(jsonObject.getString("nickname")).append(" => ");
                comentarios.append(jsonObject.getString("contenido")).append("\n");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        this.txtComentarios.setText(comentarios.toString());
        return comentarios.toString();
    }
}