package com.example.recycler.model;

import org.apache.commons.validator.routines.EmailValidator;

public class MiembroOfercompas {
    private int idMiembro;
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

    public static boolean validarEmail(String email){
        boolean emailValido = false;
        EmailValidator validator = EmailValidator.getInstance();
        if (validator.isValid(email)) {
            emailValido = true;
        }
        return  emailValido;
    }

    public  static boolean validarNickname(String nickname){
        boolean valido = false;
        if(nickname.length()<= 20 && nickname.length()>=4){
            valido = true;
        }
        return  valido;
    }
    public static boolean validadarContrasenia(String contrasenia){
        boolean valida = false;
        if(contrasenia.length()>=6 &&  contrasenia.length()<=15){
            valida = true;
        }
        return  valida;
    }
}
