package com.example.recycler.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Multimedia {
    private File archivo;
    private String url;

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(String path) {
        this.archivo = new File(path);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int publicarArchivo(int idPublicacion) throws IOException {
        File archivo = this.archivo;
        System.out.println(archivo);
        /*
        API api = new API();
        HashMap resultados = api.enviarFormulario("POST", "publicaciones/" + idPublicacion + "/multimedia", null, null, API.getToken(), archivo);
        return (int) resultados.get("status");

         */
        return 0;
    }

    public int actualizar(int idPublicacion) throws IOException {
        File archivo = this.archivo;
        System.out.println(archivo);
        /*
        API api = new API();
        HashMap resultados = api.enviarFormulario("PUT", "publicaciones/" + idPublicacion + "/multimedia", null, null, API.getToken(), archivo);

         */
        return 0;

    }
}
