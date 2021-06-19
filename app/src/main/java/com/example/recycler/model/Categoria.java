package com.example.recycler.model;

public enum Categoria {
    TECNOLOGIA(1),
    MODAMUJER(2),
    MODAHOMBRE(3),
    HOGAR(4),
    MASCOTAS(5),
    VIAJE(6),
    ENTRETENIMIENTO(7),
    COMIDABEBIDA(8);

    private int indice;

    Categoria(int indice) {
        this.indice = indice;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
}
