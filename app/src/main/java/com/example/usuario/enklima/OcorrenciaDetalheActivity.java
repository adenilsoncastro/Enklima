package com.example.usuario.enklima;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.usuario.enklima.Model.Occurrence;
import com.example.usuario.enklima.Model.User;

public class OcorrenciaDetalheActivity extends AppCompatActivity {

    private TextView txtTitulo;
    private TextView txtDetalhe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocorrencia_detalhe);

        Intent i = getIntent();
        Occurrence ocorrencia = (Occurrence)i.getSerializableExtra("ocorrencia");

        txtTitulo = (TextView) findViewById(R.id.lblTituloDetalhe);
        txtDetalhe = (TextView) findViewById(R.id.lblDescricaoDetalhe);

        txtTitulo.setText(ocorrencia.getTitulo());
        txtDetalhe.setText(ocorrencia.getDescricao());

    }

    public void btnVoltarClick(View v){
        finish();
    }
}
