package com.example.recycler.sesion;

import android.app.Application;

public class MiembroOfercompasSesion extends Application {
    private static int idMiembro = 0;
    private static String email = null;
    private static String nickname = null;
    private static String token = "";
    private static int tipoMiembro = 1;
    private static  String contrasenia = "";
    public static String ipSever = "http://192.168.56.1:5000/";

    public static int getIdMiembro() {
        return idMiembro;
    }

    public static void setIdMiembro(int idMiembro) {
        MiembroOfercompasSesion.idMiembro = idMiembro;
    }

    public static String getContrasenia() {
        return contrasenia;
    }

    public static void setContrasenia(String contrasenia) {
        MiembroOfercompasSesion.contrasenia = contrasenia;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        MiembroOfercompasSesion.email = email;
    }

    public static String getNickname() {
        return nickname;
    }

    public static void setNickname(String nickname) {
        MiembroOfercompasSesion.nickname = nickname;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        MiembroOfercompasSesion.token = token;
    }

    public static int getTipoMiembro() {
        return tipoMiembro;
    }

    public static void setTipoMiembro(int tipoMiembro) {
        MiembroOfercompasSesion.tipoMiembro = tipoMiembro;
    }
}
