package com.example.recycler.model;

import org.apache.commons.validator.routines.EmailValidator;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
    public static String encriptar(String value) {
        try {
            String clave = "FooBar1234567890"; // 128 bit
            byte[] iv = new byte[16];
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec sks = new SecretKeySpec(clave.getBytes("UTF-8"), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, sks, new IvParameterSpec(iv));

            byte[] encriptado = cipher.doFinal(value.getBytes());
            String contrasenia = android.util.Base64.encodeToString(encriptado, 16);
            contrasenia = contrasenia.substring(0, contrasenia.length()-1);

            return contrasenia;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
