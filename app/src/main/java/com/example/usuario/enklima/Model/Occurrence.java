package com.example.usuario.enklima.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Occurrence {
    private String id;
    private String titulo;
    private String descricao;
    private byte[] image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void JSonToOccurrence(String json) throws JSONException {
        JSONObject jsonObj = new JSONObject(json);

        this.id = jsonObj.getString("_id");
        this.titulo = jsonObj.getString("title");
        this.descricao = jsonObj.getString("details");
        this.image = jsonObj.getString("image").getBytes();
    }
}
