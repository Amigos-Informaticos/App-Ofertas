package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.SearchView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.recycler.adaptador.OfertaAdapter;
import com.example.recycler.comunicacion.MetaStringRequest;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.MiembroOfercompas;
import com.example.recycler.model.Oferta;
import com.example.recycler.sesion.MiembroOfercompasSesion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OfertaAdapter.RecyclerItemClick, SearchView.OnQueryTextListener {
    private RecyclerView rvLista;
    private SearchView svSearch;
    private OfertaAdapter adapter;
    private List<Oferta> ofertasRecuperadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        obtenerOfertas();
        initViews();

    }


    private void initViews(){
        rvLista = findViewById(R.id.rvLista);
        svSearch = findViewById(R.id.svSearch);
    }

    private void initValues() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(manager);
        adapter = new OfertaAdapter(ofertasRecuperadas, this);
        adapter.setConetext(this);
        rvLista.setAdapter(adapter);
    }

    private void initListener() {
        svSearch.setOnQueryTextListener(this);
    }


    @Override
    public void itemClick(Oferta item) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("itemDetail", item);
        startActivity(intent);
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


    public void obtenerOfertas() {
        String url = MiembroOfercompasSesion.ipSever+"ofertas";
        Log.e("IP", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        convertirOfertas(array);



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

    public void convertirOfertas(JSONArray array){
        this.ofertasRecuperadas = new ArrayList<>();

        for(int i=0;i<array.length();i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = array.getJSONObject(i);
                Oferta oferta = new Oferta();
                oferta.setIdPublicacion(jsonObject.getInt("idPublicacion"));
                oferta.setTitulo(jsonObject.getString("titulo"));
                oferta.setDescripcion(jsonObject.getString("descripcion"));
                oferta.setFechaCreacion(jsonObject.getString("fechaCreacion"));
                oferta.setFechaFin(jsonObject.getString("fechaFin"));
                oferta.setPrecio((float) jsonObject.getDouble("precio"));
                oferta.setVinculo(jsonObject.getString("vinculo"));
                oferta.setIdPublicador(jsonObject.getInt("publicador"));
                oferta.setPuntuacion(jsonObject.getInt("puntuacion"));
                oferta.setURLFoto(jsonObject.getString("imagen"));
                ofertasRecuperadas.add(oferta);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        initValues();
        initListener();

    }


    public void clickPerfil(View view) {
        Intent miIntent = new Intent(this, Perfil.class);
        startActivity(miIntent);
    }

    public void publicar(View view) {
        Intent miIntent = new Intent(this, PublicarOferta.class);
        startActivity(miIntent);
    }

    public void clicCodigos(View view) {
        Intent miIntent = new Intent(this, InicioCodigosDescuento.class);
        startActivity(miIntent);
    }

    public void clicAtras(View view) {
    }

    public void clicReporte(View view) {
        Intent miIntent = new Intent(this, MiembrosDenunciados.class);
        startActivity(miIntent);
    }
}
