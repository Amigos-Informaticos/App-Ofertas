package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.recycler.adaptador.CodigoAdapter;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.CodigoDescuento;
import com.example.recycler.sesion.MiembroOfercompasSesion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InicioCodigosDescuento extends AppCompatActivity implements CodigoAdapter.RecyclerItemClick, SearchView.OnQueryTextListener {
    private RecyclerView rvLista;
    private SearchView svSearch;
    private CodigoAdapter adapter;
    private List<CodigoDescuento> codigosRecuperados;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_codigos_descuento);
        obtenerCodigos();
        initViews();
    }
    private void initViews(){
        rvLista = findViewById(R.id.rvLista);
        svSearch = findViewById(R.id.svSearch);
    }
    private void initValues() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(manager);
        adapter = new CodigoAdapter(codigosRecuperados, this);
        rvLista.setAdapter(adapter);
    }
    private void initListener() {
        svSearch.setOnQueryTextListener(this);
    }



    
    
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
    }

    @Override
    public void itemClick(CodigoDescuento item) {
        Intent intent = new Intent(this, DetailCodigoDescuento.class);
        intent.putExtra("itemDetail", item);
        startActivity(intent);

    }
    public void obtenerCodigos() {
        String url = MiembroOfercompasSesion.ipSever+"codigos";
        Log.e("IP", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        convertirCodigos(array);


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

    public void convertirCodigos(JSONArray array){
        this.codigosRecuperados = new ArrayList<>();

        for(int i=0;i<array.length();i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = array.getJSONObject(i);
                CodigoDescuento codigo = new CodigoDescuento();
                codigo.setIdPublicacion(jsonObject.getInt("idPublicacion"));
                codigo.setTitulo(jsonObject.getString("titulo"));
                codigo.setDescripcion(jsonObject.getString("descripcion"));
                codigo.setFechaCreacion(jsonObject.getString("fechaCreacion"));
                codigo.setFechaFin(jsonObject.getString("fechaFin"));
                codigo.setIdPublicador(jsonObject.getInt("publicador"));
                codigo.setPuntuacion(jsonObject.getInt("puntuacion"));
                codigo.setImgResource(R.drawable.vegeta_super_blue);
                codigosRecuperados.add(codigo);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        initValues();
        initListener();

    }

    public void clickPerfil(View view) {
    }

    public void clicSiguiente(View view) {
    }

    public void clicAtras(View view) {
    }

    public void clicPublicar(View view) {
    }

    public void clicReporteUsuarios(View view) {
    }

    public void reporteOfertas(View view) {
    }

}