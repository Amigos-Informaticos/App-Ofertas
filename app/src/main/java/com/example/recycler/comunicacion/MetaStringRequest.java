package com.example.recycler.comunicacion;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.recycler.sesion.MiembroOfercompasSesion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class MetaStringRequest extends StringRequest {

    public MetaStringRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public MetaStringRequest(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public static String getCookies() {
        return cookies;
    }

    public static void setCookies(String cookies) {
        MetaStringRequest.cookies = cookies;
    }

    private static String cookies = "";
/*
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        // since we don't know which of the two underlying network vehicles
        // will Volley use, we have to handle and store session cookies manually
        Log.i("response",response.headers.toString());
        Map<String, String> responseHeaders = response.headers;
        String rawCookies = responseHeaders.get("Set-Cookie");
        Log.i("cookies",rawCookies);
        return super.parseNetworkResponse(response);
    }

 */


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = "";
            JSONObject jsonResponse = new JSONObject();
            if (response.data != null && response.data.length > 0) {
                jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers));
                jsonResponse = new JSONObject(jsonString);
            }
            jsonResponse.put("headers", new JSONObject(response.headers));
            JSONObject headers = (JSONObject) jsonResponse.get("headers");
            if (headers.has("Set-Cookie")) {
                MetaStringRequest.cookies = headers.get("Set-Cookie").toString();
            }
            jsonResponse.put("status", response.statusCode);

            String stringResponse = jsonResponse.toString();

            return Response.success(stringResponse,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> mapa = new HashMap<String, String>();
        if (!MetaStringRequest.cookies.equals("")) {
            mapa.put("Cookie", MetaStringRequest.cookies);
        }
        if (!MiembroOfercompasSesion.getToken().equals("")) {
            mapa.put("token", MiembroOfercompasSesion.getToken());
        }

        return mapa;
    }


}