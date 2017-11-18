package com.example.usuario.enklima;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.usuario.enklima.Model.User;

public class BoasVindasActivity extends AppCompatActivity {

    private TextView txtBoasVindas;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boas_vindas);

        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");

        txtBoasVindas = (TextView) findViewById(R.id.txtBoasVindas);
        txtBoasVindas.setText("Bem vindo(a), " + user.getLogin().toUpperCase());
    }

    public void RegistrarOcorrencia(View v) {
        Intent i = new Intent(this, RegistrarOcorrenciaActivity.class);
        i.putExtra("user", user);
        startActivity(i);
    }

    public void ConsultarOcorrencias(View v) {
        Intent i = new Intent(this, ConsultarOcorrenciasActivity.class);
        i.putExtra("user", user);
        startActivity(i);
    }

    public void Sair(View v) {
        finish();
    }
}
