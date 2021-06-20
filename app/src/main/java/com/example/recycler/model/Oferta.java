package com.example.recycler.model;

import android.util.Log;

import com.android.volley.Request;
import com.example.recycler.comunicacion.MetaRequest;
import com.example.recycler.sesion.MiembroOfercompasSesion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Oferta extends Publicacion implements Serializable {
    private int imgResource;
    private float precio;
    private String vinculo;

    @Override
    public String toString() {
        return "Oferta{" +
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imgResource=" + imgResource +
                ", idPublicacion=" + idPublicacion +
                ", precio=" + precio +
                ", vinculo='" + vinculo + '\'' +
                ", categoria=" + categoria +
                ", publicador=" + idPublicador +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", puntuacion=" + puntuacion +
                '}';
    }


    public Oferta() {
        super();
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    public boolean estaCompleto() {
        return this.titulo != null &&
                this.descripcion != null &&
                this.precio != -1 &&
                this.fechaCreacion != null &&
                this.fechaFin != null &&
                this.categoria != 0 &&
                this.vinculo != null;
    }

    public int publicar() throws JSONException {
        AtomicInteger respuesta = new AtomicInteger(400);
        Log.d("OfertaPublicada", this.toString());
        if (this.estaCompleto()) {
            JSONObject payload = obtenerJson();
            String url = MiembroOfercompasSesion.ipSever + "ofertas";
            MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.POST, url, payload,
                    response -> {
                        try {
                            respuesta.set(response.getInt("status"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> Log.e("ERROR PUB", "PUBLICAR"));
            ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
        } else {
            respuesta.set(400);
        }
        return respuesta.get();
    }

    public JSONObject obtenerJson() throws JSONException {
        JSONObject ofertaJson = new JSONObject();
        ofertaJson.put("titulo", this.titulo);
        ofertaJson.put("descripcion", this.descripcion);
        ofertaJson.put("fechaCreacion", this.fechaCreacion);
        ofertaJson.put("fechaFin", this.fechaFin);
        ofertaJson.put("categoria", String.valueOf(this.categoria));
        ofertaJson.put("vinculo", this.getVinculo());
        ofertaJson.put("precio", this.precio);
        ofertaJson.put("publicador", this.idPublicador);
        System.out.println(ofertaJson.toString());

        return ofertaJson;
    }

    public int actualizar() throws JSONException {
        AtomicInteger respuesta = new AtomicInteger(400);
        Log.d("OfertaPublicada", this.toString());
        if (this.estaCompleto()) {
            JSONObject payload = obtenerJson();
            String url = MiembroOfercompasSesion.ipSever + "ofertas/"+this.idPublicacion;
            MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.PUT, url, payload,
                    response -> {
                        try {
                            respuesta.set(response.getInt("status"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> Log.e("ERROR PUB", "PUBLICAR"));
            ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
        } else {
            respuesta.set(400);
        }
        return respuesta.get();
    }
}
