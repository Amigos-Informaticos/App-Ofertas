package com.example.recycler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.recycler.comunicacion.MetaRequest;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.MiembroOfercompas;
import com.example.recycler.sesion.MiembroOfercompasSesion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActualizarPerfil extends AppCompatActivity {
    private EditText txtEmail;
    private EditText txtNickName;
    private EditText txtContrasenia;
    private MiembroOfercompas miembroOfercompas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_perfil);
        this.initViews();

    }
    private void instanciarMiembroContraseña(){
        this.miembroOfercompas = new MiembroOfercompas();
        this.miembroOfercompas.setNickname(this.txtNickName.getText().toString());
        this.miembroOfercompas.setEmail(this.txtEmail.getText().toString());
        this.miembroOfercompas.setContrasenia(this.txtContrasenia.getText().toString());
    }
    private void instanciarMiembroSinContraseña(){
        this.miembroOfercompas = new MiembroOfercompas();
        this.miembroOfercompas.setNickname(this.txtNickName.getText().toString());
        this.miembroOfercompas.setEmail(this.txtEmail.getText().toString());
        this.miembroOfercompas.setContrasenia(MiembroOfercompasSesion.getContrasenia());
    }

    private void initViews(){
        this.txtNickName = findViewById(R.id.txtNickNameActualizar);
        this.txtEmail = findViewById(R.id.txtEmailActualizar);
        this.txtNickName.setText(MiembroOfercompasSesion.getNickname());
        this.txtEmail.setText(MiembroOfercompasSesion.getEmail());
        this.txtContrasenia = findViewById(R.id.txtPasswordActualizar);
    }
    private boolean camposValidosSinContrasenia(){
        boolean camposValidos = false;
        if(validarEmail() && validarNickname()){
            camposValidos = true;
        }
        return camposValidos;
    }
    private  boolean camposValidosConContrasenia(){
        boolean camposValidos = false;
        if(camposValidosSinContrasenia() && validarContrasenia()){
            camposValidos = true;
        }
        return camposValidos;
    }

    private boolean validarContrasenia(){
        boolean contraseniaValida = false;
        if(MiembroOfercompas.validadarContrasenia(this.txtContrasenia.getText().toString())){
            contraseniaValida = true;
        }else{
            mostrarMensaje("Contraseña inválida, debe tener entre 6 y 15 caracteres");
        }
        return  contraseniaValida;
    }


    private boolean validarEmail(){
        boolean txtEmailVacio = false;
        boolean emailValido = false;
        if(this.txtEmail.getText().toString().isEmpty()){
            txtEmailVacio = true;
            mostrarMensaje("Email Vacio");
        }
        if(!txtEmailVacio){
            emailValido = MiembroOfercompas.validarEmail(this.txtEmail.getText().toString());
            if(!emailValido){
                mostrarMensaje("Email inválido");
            }
        }
        return emailValido;
    }

    private boolean validarNickname(){
        boolean emailVacio = false;
        boolean emailValido = false;
        if(this.txtNickName.getText().toString().isEmpty()){
            emailVacio = true;
            mostrarMensaje("Nickname Vacio");
        }

        if(!emailVacio){
            emailValido = MiembroOfercompas.validarNickname(this.txtNickName.getText().toString());
            if(!emailValido){
                mostrarMensaje("Nickname inválido, debe contener al menos 4 caracteres y máximo que 20");
            }
        }

        return emailValido;
    }

    private void enviarActualizacion(){
        Log.e("TOKEN", MiembroOfercompasSesion.getToken());

        JSONObject object = new JSONObject();
        try {
            object.put("nickname", miembroOfercompas.getNickname());
            object.put("email",miembroOfercompas.getEmail());
            object.put("contrasenia", miembroOfercompas.getContrasenia());

            Log.e("JSON armado", object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        String url = "http://192.168.100.10:5000/miembros/" + MiembroOfercompasSesion.getEmail();
        MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.PUT, url, object,
                response -> {
                    try {
                        MiembroOfercompasSesion.setEmail(response.getString("email"));
                        MiembroOfercompasSesion.setNickname(response.getString("nickname"));
                        MiembroOfercompasSesion.setContrasenia(response.getString("contrasenia"));

                        Log.e("DATOS", response.toString() );


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {mostrarMensaje(error.networkResponse.toString());});
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
    }


    public void clicActualizar(View view) {
        if(txtContrasenia.getText().toString().equals("")){
            if(camposValidosSinContrasenia()){
                instanciarMiembroSinContraseña();
                enviarActualizacion();
            }
        }else{
            if(camposValidosConContrasenia()){
                instanciarMiembroContraseña();
                enviarActualizacion();
            }
        }
    }

    private void mostrarMensaje(String mensaje){
        AlertDialog.Builder alerta = new AlertDialog.Builder(ActualizarPerfil.this);
        alerta.setTitle(mensaje);
        alerta.show();
    }
}