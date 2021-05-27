package com.example.recycler.model;

public class MiembroOfercompas {
    private String nickname;
    private String contrasenia;
    private String email;
    private String tipoMiembro;
    private  String estadoMiembro;

    public MiembroOfercompas() {

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoMiembro() {
        return tipoMiembro;
    }

    public void setTipoMiembro(String tipoMiembro) {
        this.tipoMiembro = tipoMiembro;
    }

    public String getEstadoMiembro() {
        return estadoMiembro;
    }

    public void setEstadoMiembro(String estadoMiembro) {
        this.estadoMiembro = estadoMiembro;
    }
}
