package com.example.recycler.model;

public class DetalleMiembroDenunciado {
    private int idMiembro;
    private int numeroPublicacionesDenunciadas;
    private String nickname;

    public int getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(int idMiembro) {
        this.idMiembro = idMiembro;
    }

    public int getNumeroPublicacionesDenunciadas() {
        return numeroPublicacionesDenunciadas;
    }

    public void setNumeroPublicacionesDenunciadas(int numeroPublicacionesDenunciadas) {
        this.numeroPublicacionesDenunciadas = numeroPublicacionesDenunciadas;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "idMiembro: " + idMiembro +
                ", \nNumeroPublicacionesDenunciadas: " + numeroPublicacionesDenunciadas +
                ", \nnickname: " + nickname;
    }
}
