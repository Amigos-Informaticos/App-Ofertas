package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.recycler.comunicacion.MetaRequest;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.MiembroOfercompas;
import com.example.recycler.sesion.MiembroOfercompasSesion;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrarMiembroOfercompas extends AppCompatActivity {
    private EditText txtNickame;
    private EditText txtCorreo;
    private EditText txtContrasenia;
    private EditText txtConfirmarContrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_miembro_ofercompas);
        initViews();
    }

    private void initViews(){
        this.txtNickame = findViewById(R.id.txtNombreUsuario);
        this.txtCorreo = findViewById(R.id.txtCorreoElectronicoRe);
        this.txtContrasenia = findViewById(R.id.txtPasswordRr);
        this.txtConfirmarContrasenia = findViewById(R.id.txtConfirmarContrasenia);

    }

    private boolean validarContrasenias(){
        boolean valida = false;
        String contrasenia = this.txtContrasenia.getText().toString();
        String confirmarContrasenia = this.txtConfirmarContrasenia.getText().toString();
        if(contrasenia.equals(confirmarContrasenia)){
            if(MiembroOfercompas.validadarContrasenia(contrasenia)){
                valida = true;
            }
        }
        return valida;
    }

    private boolean validarCampos(){
        String nickname = txtNickame.getText().toString();
        String correo = txtCorreo.getText().toString();
        boolean validos = false;


        if(MiembroOfercompas.validarNickname(nickname)
            && MiembroOfercompas.validarEmail(correo)
        ){
            if(validarContrasenias()){

                validos = true;
            }else{
                mostrarMensaje("Contraseña inválida");
            }

        }else {
            mostrarMensaje("Email o nickname inválidos");
        }

        return validos;
    }
    private void mostrarMensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void registrarMiembro(){
        JSONObject object = new JSONObject();
        try {
            String contrasenia = this.txtContrasenia.getText().toString();
            String contraseniaEncryp = MiembroOfercompas.encriptar(contrasenia);
            object.put("email",this.txtCorreo.getText().toString());
            object.put("contrasenia",contraseniaEncryp);
            object.put("nickname", this.txtNickame.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        String url = MiembroOfercompasSesion.ipSever+ "/miembros";
        MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mostrarMensaje("REGISTRADO EXITOSAMENTE");
                        regresarLogin();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse!= null){
                    mostrarMensaje("EL MIEMBRO YA HA SIDO REGISTRADO PREVIAMENTE");
                    mostrarMensaje("CAMBIE SU CONTRASEÑA Y SU EMAIL");

                }else{
                    mostrarMensaje("ERROR AL CONECTARSE CON EL SERVIDOR");

                }


            }

        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    private  void regresarLogin(){
        Intent miIntent = new Intent(this, LoginOfercompas.class);
        startActivity(miIntent);
    }
    public void registrarseClick(View view) {
        if(validarCampos()){
            registrarMiembro();
        }

    }

}