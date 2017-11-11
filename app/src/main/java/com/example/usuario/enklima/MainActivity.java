package com.example.usuario.enklima;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.enklima.Model.User;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String wsUrl = "http://35.167.241.68:3000";
    private final String wsLoginUrl = wsUrl + "/users/login";


    private EditText login;
    private EditText password;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (EditText)findViewById(R.id.txtLogin);
        password = (EditText)findViewById(R.id.txtPassword);

        queue = Volley.newRequestQueue(this);
    }

    public void login(View v){

        final User user = new User();

        user.setLogin(login.getText().toString());
        user.setPassword(password.getText().toString());

        StringRequest postRequest = new StringRequest(Request.Method.POST, wsLoginUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("login", user.getLogin());
                params.put("password", user.getPassword());

                return params;
            }
        };
        queue.add(postRequest);

    }
}