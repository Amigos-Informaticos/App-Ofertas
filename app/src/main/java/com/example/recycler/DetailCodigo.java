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
import com.example.recycler.model.CodigoDescuento;
import com.example.recycler.model.Publicacion;
import com.example.recycler.sesion.MiembroOfercompasSesion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailCodigo extends AppCompatActivity {
    private ImageView imgItemDetail;
    private TextView tvTituloDetail;
    private TextView tvDescripcion;
    private EditText txtComentario;
    private TextView tvCodigo;
    private TextView tvPuntuacion;
    private Button btnLike;
    private Button btnDisike;
    private Button btnComenta;
    private Button btnDenunciar;
    private Button btnEliminar;
    private Button btnActualizar;
    private TextView txtComentarios;

    private CodigoDescuento codigoDescuento;
    public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_codigo);
        setTitle(getClass().getSimpleName());

        initViews();
        initValues();
        obtenerInteraccion();
        validarUsuario();
        obtenerComentarios();
        funcionEliminar();
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


    private void obtenerComentarios() {
        String url = MiembroOfercompasSesion.ipSever + "publicaciones/" +codigoDescuento.getIdPublicacion() + "/comentarios";
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

    private void validarUsuario() {
        if(codigoDescuento.getIdPublicador() != MiembroOfercompasSesion.getIdMiembro()){
            if(MiembroOfercompasSesion.getTipoMiembro()!=2){
                this.btnActualizar.setVisibility(View.GONE);
                this.btnEliminar.setVisibility(View.GONE);
            }else{
                this.btnActualizar.setVisibility(View.GONE);
            }
        }
    }

    private void obtenerInteraccion() {
        String url = MiembroOfercompasSesion.ipSever + "publicaciones/" +codigoDescuento.getIdPublicacion() + "/interaccion";
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

    private void initViews() {
        imgItemDetail = findViewById(R.id.imgItemDetail);
        tvTituloDetail = findViewById(R.id.tvTituloDetail);
        tvDescripcion = findViewById(R.id.tvDescripcion_codigo);
        txtComentario = findViewById(R.id.txtComentario_codigo);
        tvCodigo = findViewById(R.id.tvCodigo);
        tvPuntuacion = findViewById(R.id.tvPuntuacion_codigo);
        btnLike = findViewById(R.id.btnLike_codigo);
        btnDisike = findViewById(R.id.btnDislike_codigo);
        btnEliminar = findViewById(R.id.btnEliminar_codigo);
        btnActualizar = findViewById(R.id.btnActualizar_codigo);
        btnDenunciar = findViewById(R.id.btnDenuncia_codigo);
        txtComentarios = findViewById(R.id.txtComentarios_codigos);
    }

    private void initValues() {
        codigoDescuento = (CodigoDescuento) getIntent().getExtras().getSerializable("itemDetail");
        Log.d("OFERTA A VER: ", codigoDescuento.toString());
        tvTituloDetail.setText(codigoDescuento.getTitulo());
        tvDescripcion.setText(codigoDescuento.getDescripcion());
        tvCodigo.setText(String.valueOf(codigoDescuento.getCodigo()));
        tvPuntuacion.setText(String.valueOf(codigoDescuento.getPuntuacion()));

        imgItemDetail.setImageResource(codigoDescuento.getImgResource());
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

    public void like_codigo(View view) {
        int puntuacion = codigoDescuento.getPuntuacion() +1;
        this.tvPuntuacion.setText(puntuacion + "");
        this.puntuarCodigo(false);
        obtenerInteraccion();
    }

    public void dislike_codigo(View view) {
        int puntuacion = codigoDescuento.getPuntuacion() -1;
        this.tvPuntuacion.setText(puntuacion + "");
        this.puntuarCodigo(true);
        obtenerInteraccion();
    }
    public  void puntuarCodigo(boolean esPositiva){
        JSONObject object = new JSONObject();
        try {
            object.put("esPositiva",esPositiva);
            object.put("idMiembro",MiembroOfercompasSesion.getIdMiembro());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        String url = MiembroOfercompasSesion.ipSever+ "publicaciones/" + codigoDescuento.getIdPublicacion()+ "/puntuaciones";
        MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String puntuacionStr = tvPuntuacion.getText().toString();
                        int puntuacion = Integer.parseInt(puntuacionStr);
                        mostrarMensaje("Like registrado");

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
    private void mostrarMensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }


    public void clicComentar(View view) {
        comentar();
    }

    private void comentar() {
        if(Publicacion.comentarioValido(this.txtComentario.getText().toString())){
            JSONObject object = new JSONObject();
            try {
                object.put("contenido",this.txtComentario.getText().toString());
                object.put("idMiembro",MiembroOfercompasSesion.getIdMiembro());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Enter the correct url for your api service site
            String url = MiembroOfercompasSesion.ipSever+ "publicaciones/" + codigoDescuento.getIdPublicacion()+ "/comentarios";
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

    public void denunciarCodigo(View view) {
        MiembroOfercompasSesion.tituloPublicacionDenunciar = codigoDescuento.getTitulo();
        MiembroOfercompasSesion.idPublicacionDenunciar = codigoDescuento.getIdPublicacion();
        Intent miIntent = new Intent(this, DenunciarPublicacion.class);
        startActivity(miIntent);
    }
}