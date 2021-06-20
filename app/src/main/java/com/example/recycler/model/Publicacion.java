package com.example.recycler.model;

import android.util.Log;

import com.android.volley.Request;
import com.example.recycler.DetailActivity;
import com.example.recycler.comunicacion.MetaRequest;
import com.example.recycler.sesion.MiembroOfercompasSesion;

import org.json.JSONException;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Publicacion implements Serializable {
    protected int idPublicacion;
    protected String titulo;
    protected String descripcion;
    protected String fechaCreacion;
    protected String fechaFin;
    protected int puntuacion;
    protected int estado;
    protected int categoria = 0;
    protected int idPublicador;
    protected int imgResource;

    public Publicacion() {

    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    /*
    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
        api = new API();
        this.api.setURL("http://127.0.0.1");
        api.setPort(5000);
    }

    public Publicacion() {
        api = new API();
        this.api.setURL("http://127.0.0.1");
        api.setPort(5000);

    }

     */

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public int getIdPublicador() {
        return idPublicador;
    }

    public void setIdPublicador(int idPublicador) {
        this.idPublicador = idPublicador;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public Publicacion(String titulo, String descripcion, String fechaInicio, String fechaFin, int puntuacion, int estado, int categoria) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaInicio;
        this.fechaFin = fechaFin;
        this.puntuacion = puntuacion;
        this.estado = estado;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Publicacion{" +
                "ID " + idPublicacion + '\'' +
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", puntuacion=" + puntuacion +
                ", estado=" + estado +
                ", categoria=" + categoria +
                ", publicador=" + idPublicador +
                '}';
    }

    public boolean estaCompleta() {
        return this.titulo != null && this.descripcion != null && this.fechaCreacion != null && this.fechaFin != null && this.categoria != 0;
    }

    public void setCategoriaCmbBox(String categoria) {
        switch (categoria) {
            case "Tecnologia":
                this.categoria = Categoria.TECNOLOGIA.getIndice();
                break;
            case "Moda de mujer":
                this.categoria = Categoria.MODAMUJER.getIndice();
                break;
            case "Moda de hombre":
                this.categoria = Categoria.MODAHOMBRE.getIndice();
                break;
            case "Hogar":
                this.categoria = Categoria.HOGAR.getIndice();
                break;
            case "Mascotas":
                this.categoria = Categoria.MASCOTAS.getIndice();
                break;
            case "Viaje":
                this.categoria = Categoria.VIAJE.getIndice();
                break;
            case "Entretenimiento":
                this.categoria = Categoria.COMIDABEBIDA.getIndice();
                break;
            case "Comida y bebida":
                this.categoria = Categoria.COMIDABEBIDA.getIndice();
                break;
            default:
                this.categoria = Categoria.TECNOLOGIA.getIndice();
                break;
        }

    }

    public String deCategoriaACmbBox() {
        String categoriaString = "";
        int categoria = this.categoria;
        switch (categoria) {
            case 1:
                categoriaString = "Tecnologia";
                break;
            case 2:
                categoriaString = "Moda de mujer";
                break;
            case 3:
                categoriaString = "Moda de hombre";
                break;
            case 4:
                categoriaString = "Hogar";
                break;
            case 5:
                categoriaString = "Mascotas";
                break;
            case 6:
                categoriaString = "Viaje";
                break;
            case 7:
                categoriaString = "Entretenimiento";
                break;
            case 8:
                categoriaString = "Comida y bebida";
                break;
            default:
                categoriaString = "Tecnologia";
                break;
        }
        return categoriaString;
    }

    /*
        public int puntuar(int idMiembro, int esPositiva) throws IOException {
            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idMiembro", String.valueOf(idMiembro));
            parametros.put("esPositiva", String.valueOf(esPositiva));
            HashMap respuesta = api.connect("POST", ("publicaciones/" + this.idPublicacion + "/puntuaciones"), null, parametros);
            return (int) respuesta.get("status");
        }

    public int eliminar() {
        AtomicInteger respuesta = new AtomicInteger(400);
        String url = MiembroOfercompasSesion.ipSever + "ofertas/" + this.idPublicacion;
        MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.DELETE, url, null,
                response -> {
                    DetailActivity.regresarM


                }, error -> {
            Log.e("ERROR ELIMINAR", error.getMessage());
            Log.d("STATUS ELIMINAR:", String.valueOf(error.networkResponse.statusCode));
        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
        Log.d("ELIMINO:", String.valueOf(respuesta.get()));
        return respuesta.get();
    }

     */

    public static  boolean comentarioValido(String comentario){
        boolean comentarioValido = true;
        if(comentario!= null){
            if(comentario.length()>5){

            }
        }
        return  comentarioValido;
    }
}