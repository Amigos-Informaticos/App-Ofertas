package com.example.recycler.comunicacion;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.recycler.sesion.MiembroOfercompasSesion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MetaRequest extends JsonObjectRequest {
    public static String getCookies() {
        return cookies;
    }

    public static void setCookies(String cookies) {
        MetaRequest.cookies = cookies;
    }

    private static String cookies = "";

    public MetaRequest(int method, String url, JSONObject jsonRequest, Response.Listener
            <JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public MetaRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject>
            listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            JSONObject jsonResponse = new JSONObject(jsonString);
            jsonResponse.put("headers", new JSONObject(response.headers));
            JSONObject headers = (JSONObject) jsonResponse.get("headers");
            if(headers.has("Set-Cookie")){
                MetaRequest.cookies = headers.get("Set-Cookie").toString();
            }
            jsonResponse.put("status",response.statusCode);


            return Response.success(jsonResponse,
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
        if(!MetaRequest.cookies.equals("")){
            mapa.put("Cookie", MetaRequest.cookies);
        }
        if(!MiembroOfercompasSesion.getToken().equals("")){
            mapa.put("token", MiembroOfercompasSesion.getToken());
        }

        return  mapa;
    }


}