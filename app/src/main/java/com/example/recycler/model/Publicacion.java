package com.example.recycler.model;

import com.example.recycler.model.Categoria;

import java.io.IOException;
import java.util.HashMap;

public abstract class Publicacion {
    protected String titulo;
    protected String descripcion;
    protected String fechaCreacion;
    protected String fechaFin;
    protected int puntuacion;
    protected int estado;
    protected int categoria = 0;
    protected int idPublicacion;

    public int getIdPublicador() {
        return idPublicador;
    }

    public void setIdPublicador(int idPublicador) {
        this.idPublicador = idPublicador;
    }

    protected int idPublicador;


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

    public int eliminar() throws IOException {
        HashMap respuesta = this.api.connect("DELETE", ("publicaciones/"+this.idPublicacion), null, null);
        return (int) respuesta.get("status");
    }

 */
}