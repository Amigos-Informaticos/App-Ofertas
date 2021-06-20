package com.example.recycler.model;

import android.util.Log;

import com.android.volley.Request;
import com.example.recycler.comunicacion.MetaRequest;
import com.example.recycler.sesion.MiembroOfercompasSesion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CodigoDescuento extends Publicacion{
    private String codigo;

    public CodigoDescuento() {
        super();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }



    public int publicar() throws JSONException {
        AtomicInteger respuesta = new AtomicInteger(400);
        Log.d("Codigo", this.toString());
        if (this.estaCompleta()) {
            JSONObject payload = obtenerJson();
            String url = MiembroOfercompasSesion.ipSever + "codigos";
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
        Log.d("RESPUESTA", String.valueOf(respuesta.get()));
        return respuesta.get();
    }

    public JSONObject obtenerJson() throws JSONException {
        JSONObject codigoJson = new JSONObject();
        codigoJson.put("titulo", this.titulo);
        codigoJson.put("descripcion", this.descripcion);
        codigoJson.put("fechaCreacion", this.fechaCreacion);
        codigoJson.put("fechaFin", this.fechaFin);
        codigoJson.put("categoria", String.valueOf(this.categoria));
        codigoJson.put("codigo", this.codigo);
        codigoJson.put("publicador", this.idPublicador);
        System.out.println(codigoJson.toString());

        return codigoJson;
    }

    public HashMap obtenerHashmap() {
        HashMap<String, String> codigo = new HashMap();
        codigo.put("titulo", this.titulo);
        codigo.put("descripcion", this.descripcion);
        codigo.put("fechaCreacion", this.fechaCreacion);
        codigo.put("fechaFin", this.fechaFin);
        codigo.put("categoria", String.valueOf(this.categoria));
        codigo.put("publicador", String.valueOf(7));
        codigo.put("codigo", this.codigo);

        return codigo;
    }

    public boolean estaCompleta() {
        return super.estaCompleta() && this.codigo != null;
    }

    public int actualizar() throws IOException {
        /*
        HashMap respuesta = this.api.connect("PUT", ("codigos"+this.idPublicacion), null, this.obtenerHashmap(),API.getToken(), false);
        return (int) respuesta.get("status");

         */
        return 0;
    }
}
