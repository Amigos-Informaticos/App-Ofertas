package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.recycler.comunicacion.MetaRequest;
import com.example.recycler.model.ApplicationController;
import com.example.recycler.model.MiembroOfercompas;
import com.example.recycler.sesion.MiembroOfercompasSesion;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginOfercompas extends AppCompatActivity {
    private TextView txtEmail;
    private EditText txtContrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ofercompas);
        this.txtEmail = (TextView) findViewById(R.id.txtCorreoElectronico);
        this.txtContrasenia = (EditText) findViewById(R.id.txtPassword);
    }

    public void registrarseClick(View view) {
        Intent miIntent = new Intent(this, RegistrarMiembroOfercompas.class);
        startActivity(miIntent);
    }

    public void iniciarSesionClick(View view) {
        MiembroOfercompas miembroOfercompas = this.obtenerDatos();
        Log.d("ENVIANDO LOGIN", "enviando loging");
        enviarLogin(miembroOfercompas);
    }

    private MiembroOfercompas obtenerDatos(){
        MiembroOfercompas miembroOfercompas = new MiembroOfercompas();
        miembroOfercompas.setEmail(this.txtEmail.getText().toString());
        miembroOfercompas.setContrasenia(this.txtContrasenia.getText().toString());
        String contrasenia = miembroOfercompas.getEmail() + miembroOfercompas.getContrasenia();
        Log.d("OBTENIENDO DATOS", contrasenia );

        return  miembroOfercompas;
    }


    public void enviarLogin(MiembroOfercompas miembroOfercompas) {
        JSONObject object = new JSONObject();
        try {
            /*object.put("email","elmartinez@gmail.com");
            object.put("contrasenia","2FM3kO2CQerbYQiJjEaNZA==");*/

            /*object.put("email","hola@gmail.com");
            object.put("contrasenia","z6SEIOxnHpiPm1aT5kKtUw==");*/

            object.put("email",miembroOfercompas.getEmail());
            Log.e("LA CONTRASEÑA ES: ", MiembroOfercompas.encriptar(miembroOfercompas.getContrasenia()));
            String contrasenia = MiembroOfercompas.encriptar(miembroOfercompas.getContrasenia());
            object.put("contrasenia",contrasenia );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        String url = MiembroOfercompasSesion.ipSever+ "login";
        MetaRequest jsonObjectRequest = new MetaRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            MiembroOfercompasSesion.setEmail(response.getString("email"));
                            MiembroOfercompasSesion.setIdMiembro(response.getInt("idMiembro"));
                            MiembroOfercompasSesion.setNickname(response.getString("nickname"));
                            MiembroOfercompasSesion.setTipoMiembro(response.getInt("tipoMiembro"));
                            MiembroOfercompasSesion.setToken(response.getString("token"));
                            MiembroOfercompasSesion.setContrasenia(response.getString("contrasenia"));
                            LoginOfercompas.this.mostrarMenuPrincipal();
                            Log.e("DATOS", response.toString());
                            JSONObject headers = response.getJSONObject("headers");
                            Log.e("HEADERS", headers.toString());


                            //mostrarMenuPrincipal();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse!= null){
                    txtEmail.setText("String Response : " + error.networkResponse.statusCode);
                }else{
                    mostrarMensaje("ERROR AL CONECTARSE CON EL SERVIDOR");
                }


            }

        });
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);


    }


    private  void mostrarMenuPrincipal(){
        Intent miIntent = new Intent(this, MainActivity.class);
        startActivity(miIntent);
    }
    private void mostrarMensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }



}