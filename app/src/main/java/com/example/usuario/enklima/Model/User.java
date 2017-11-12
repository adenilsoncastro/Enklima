package com.example.usuario.enklima.Model;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void JSonToUser(String json) throws JSONException {
        JSONObject jsonObj = new JSONObject(json);

        this.id = jsonObj.getString("_id");
        this.login = jsonObj.getString("login");
        this.password = jsonObj.getString("password");
    }
}
