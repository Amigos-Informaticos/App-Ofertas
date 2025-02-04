package com.example.recycler.sesion;

import android.app.Application;

import com.example.recycler.DetailReporteMiembro;
import com.example.recycler.model.DetalleMiembroDenunciado;

public class MiembroOfercompasSesion extends Application {
    private static int idMiembro = 0;
    private static String email = null;
    private static String nickname = null;
    private static String token = "";
    private static int tipoMiembro = 1;
    private static  String contrasenia = "";
    public static String ipSever = "http://192.168.100.10:5000/";

    public static int idPublicacionDenunciar = 0;
    public static String tituloPublicacionDenunciar = "";
    public static DetalleMiembroDenunciado miembroDenunciado;


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
