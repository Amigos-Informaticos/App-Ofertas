package com.example.recycler.model;
import java.io.Serializable;

public class Oferta implements Serializable {
    private String titulo;
    private String descripcion;
    private int imgResource;

    //
    private int idPublicacion;
    private float precio;
    private String vinculo;
    private int categoria;
    private int publicador;
    private String fechaCreacion;
    private String fechaFin;
    private int puntuacion;


    public Oferta(String titulo, String descripcion, int imgResource) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imgResource = imgResource;
    }
    public Oferta(){

    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
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

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getPublicador() {
        return publicador;
    }

    public void setPublicador(int publicador) {
        this.publicador = publicador;
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
}
