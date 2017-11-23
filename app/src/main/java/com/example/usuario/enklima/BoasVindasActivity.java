package com.example.usuario.enklima;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usuario.enklima.Model.User;

public class BoasVindasActivity extends AppCompatActivity {

    private TextView txtBoasVindas;
    private User user;

    private ImageView imgRegister;
    private ImageView imgConsult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boas_vindas);

        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");

        txtBoasVindas = (TextView) findViewById(R.id.txtBoasVindas);
        txtBoasVindas.setText("Bem vindo(a), " + user.getLogin().toUpperCase());

        imgRegister = (ImageView)findViewById(R.id.imgRegister);
        imgRegister.setImageResource(R.drawable.ic_siren_foreground);
        imgConsult = (ImageView)findViewById(R.id.imgConsult);
        imgConsult.setImageResource(R.drawable.ic_info_foreground);

        imgRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                RegistrarOcorrencia(view);
            }
        });

        imgConsult.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ConsultarOcorrencias(view);
            }
        });

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
