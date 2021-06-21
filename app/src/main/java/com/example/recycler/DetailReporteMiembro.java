package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.recycler.adaptador.DetalleMiembroAdapter;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.DetalleMiembroDenunciado;
import com.example.recycler.model.Oferta;
import com.example.recycler.sesion.MiembroOfercompasSesion;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

public class DetailReporteMiembro extends AppCompatActivity {
    DetalleMiembroDenunciado detalle;
    TextView txtNickname;
    TextView txtNumPublicacionesDenunciadas;

    private TextView txtNumeroOfertasSinDenuncias;
    private TextView txtNumeroOfertasTotales;
    private TextView txtPuntuacionTotal;
    private TextView txtPublicacionesPositivas;
    private TextView txtPublicacionjesNegativas;
    private Button btnExpulsarMiembro;

    private int numeroOfertasDenunciadas;
    private int numeroOfertasSinDenuncias;
    private int numueroOfertasTotales;
    private int puntuacionTotal;
    private int publicacionesPositivas;
    private int publicacionesNegativas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reporte_miembro);
        initValues();
        initViews();
        obtenerReporte();
    }



    private void initValues() {
        detalle = MiembroOfercompasSesion.miembroDenunciado;
        this.txtNickname = findViewById(R.id.txtNickNameDenunciado);
        this.txtNumPublicacionesDenunciadas = findViewById(R.id.txtNumPubDen);

        this.txtNickname.setText(detalle.getNickname());
        this.txtNumPublicacionesDenunciadas.setText(detalle.getNumeroPublicacionesDenunciadas() + "  ");

    }
    private void initViews(){
        this.txtNumeroOfertasSinDenuncias = findViewById(R.id.txtPublicacionesSinDenuncias);
        this.txtNumeroOfertasTotales = findViewById(R.id.txtPublicacionesTotales);
        this.txtPuntuacionTotal = findViewById(R.id.txtPuntuacionTotal);
        this.txtPublicacionesPositivas = findViewById(R.id.txtPublicacionesPositivas);
        this.txtPublicacionjesNegativas = findViewById(R.id.txtPublicacionesNegativas);
        this.btnExpulsarMiembro = findViewById(R.id.buttonExpulsarMiembro);

    }

    private void obtenerReporte(){
        String url = MiembroOfercompasSesion.ipSever + "miembros/" + detalle.getIdMiembro() + "/reporte";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            numeroOfertasDenunciadas = jsonObject.getInt("numeroDenuncias");
                            numueroOfertasTotales = jsonObject.getInt("numeroTotalPublicaciones");
                            puntuacionTotal = jsonObject.getInt("puntuacionTotal");
                            publicacionesPositivas = jsonObject.getInt("publicacionesPositivas");
                            numeroOfertasSinDenuncias = numueroOfertasTotales -numeroOfertasDenunciadas;
                            publicacionesNegativas = numueroOfertasTotales - publicacionesPositivas;
                            caragarVistas();



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
    private void caragarVistas() {
        this.txtPublicacionjesNegativas.setText(publicacionesNegativas + " ");
        this.txtPublicacionesPositivas.setText(publicacionesPositivas + " ");
        this.txtPuntuacionTotal.setText(puntuacionTotal + "");
        this.txtNumeroOfertasTotales.setText(numueroOfertasTotales +" ");
        this.txtNumeroOfertasSinDenuncias.setText(numeroOfertasSinDenuncias + "");
        this.txtNumPublicacionesDenunciadas.setText(numeroOfertasDenunciadas + " ");
    }
    public void clicExpulsarMiembro(View view) {
        this.btnExpulsarMiembro.setEnabled(false);
        expulsarMiembro();

    }

    private void expulsarMiembro(){
        String url = MiembroOfercompasSesion.ipSever + "miembros/" + detalle.getIdMiembro() + "/expulsion";
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                       mostrarMensaje("MiembroOfercompas expulsado exitosamente");
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        btnExpulsarMiembro.setEnabled(true);

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

    private void mostrarMensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}