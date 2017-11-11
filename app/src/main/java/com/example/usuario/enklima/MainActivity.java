package com.example.usuario.enklima;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

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

    void login(){



    }
}
