package com.example.recycler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    private Button botonActualizar;
    private MiembroOfercompas miembroOfercompas;
    private boolean deseaActualizar;


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
        this.botonActualizar = findViewById(R.id.buttonActualizar);
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
        this.botonActualizar.setEnabled(false);
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
        String url = MiembroOfercompasSesion.ipSever + "miembros/" + MiembroOfercompasSesion.getIdMiembro();

        MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.PUT, url, object,
                response -> {
                    try {
                        MiembroOfercompasSesion.setEmail(response.getString("email"));
                        MiembroOfercompasSesion.setNickname(response.getString("nickname"));
                        MiembroOfercompasSesion.setContrasenia(response.getString("contrasenia"));

                        mostrarMensajeToast("Actualizacion exitosa");
                        this.botonActualizar.setEnabled(true);


                    } catch (JSONException e) {
                        this.botonActualizar.setEnabled(true);
                        e.printStackTrace();
                    }
                }, error -> {mostrarMensaje(error.networkResponse.toString());});
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
    }


    public void clicActualizar(View view) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActualizarPerfil.this);
        alertDialog.setMessage("¿Está seguro que desea actualizar su perfil?").setCancelable(true);
        alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                actualizar();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alerta = alertDialog.create();
        alerta.setTitle("Actualizar perfil");
        alerta.show();

    }
    public void actualizar(){
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
        this.botonActualizar.setEnabled(true);
        AlertDialog.Builder alerta = new AlertDialog.Builder(ActualizarPerfil.this);
        alerta.setTitle(mensaje);
        alerta.show();
    }
    private  void mostrarMensajeToast(String mensaje){
        this.botonActualizar.setEnabled(true);
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

}