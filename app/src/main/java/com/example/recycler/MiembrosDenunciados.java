package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.recycler.adaptador.CodigoAdapter;
import com.example.recycler.adaptador.DetalleMiembroAdapter;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.CodigoDescuento;
import com.example.recycler.model.DetalleMiembroDenunciado;
import com.example.recycler.sesion.MiembroOfercompasSesion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MiembrosDenunciados extends AppCompatActivity implements DetalleMiembroAdapter.RecyclerItemClick{
    private RecyclerView rvLista;
    private DetalleMiembroAdapter adapter;
    private List<DetalleMiembroDenunciado> detallesRecuperados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miembros_denunciados);
        initViews();
        obtenerMiembrosDenunciados();
    }

    private void initViews(){
        rvLista = findViewById(R.id.rvLista_denunciados);

    }
    private void initValues() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(manager);
        adapter = new DetalleMiembroAdapter(detallesRecuperados, this);
        rvLista.setAdapter(adapter);
    }


    private void obtenerMiembrosDenunciados(){
        String url = MiembroOfercompasSesion.ipSever+"miembros/reportes";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        convertirMiembros(array);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                error -> {
                    Log.e("ERROR: ", error.networkResponse.statusCode+"");
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        ApplicationController.getInstance().addToRequestQueue(stringRequest);
    }

    private void convertirMiembros(JSONArray array){
        this.detallesRecuperados = new ArrayList<>();
        for(int i=0;i<array.length();i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = array.getJSONObject(i);
                DetalleMiembroDenunciado detalle = new DetalleMiembroDenunciado();
                detalle.setNickname(jsonObject.getString("nickname"));
                detalle.setIdMiembro(jsonObject.getInt("idMiembro"));
                detalle.setNumeroPublicacionesDenunciadas(jsonObject.getInt("denuncias"));
                detallesRecuperados .add(detalle);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        initValues();

    }


    @Override
    public void itemClick(DetalleMiembroDenunciado item) {
        Intent intent = new Intent(this, DetailReporteMiembro.class);
        MiembroOfercompasSesion.miembroDenunciado = item;
        MiembroOfercompasSesion.miembroDenunciado.setNumeroPublicacionesDenunciadas(item.getNumeroPublicacionesDenunciadas());
        startActivity(intent);
    }
}