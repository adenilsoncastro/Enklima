package com.example.usuario.enklima;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.enklima.Adapter.EndlessScrollListener;
import com.example.usuario.enklima.Adapter.OcorrenciaAdapter;
import com.example.usuario.enklima.Model.Occurrence;
import com.example.usuario.enklima.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultarOcorrenciasActivity extends AppCompatActivity {

    private ListView list;
    private OcorrenciaAdapter ocorrenciaAdapter;
    private RequestQueue queue;

    private String ws = "http://35.167.241.68:5000/occurrences/getApproved";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_ocorrencias);

        queue = Volley.newRequestQueue(this);

        BuildListView();
    }

    private void BuildListView(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ws,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        List<Occurrence> listOcorrencia = new ArrayList<Occurrence>();

                        try {
                            JSONArray array = new JSONArray(response);

                            for(int i = 0 ; i < array.length() ; i++){
                                JSONObject obj = array.getJSONObject(i);
                                Occurrence ocorrencia = new Occurrence();
                                ocorrencia.JSonToOccurrence(obj.toString());
                                listOcorrencia.add(ocorrencia);
                            }
                            setList(listOcorrencia);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ConsultarOcorrenciasActivity.this, "Erro!", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ConsultarOcorrenciasActivity.this, "Deu ruim na chamada", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }

    public void setList(List<Occurrence> listOcorrencia){
        ocorrenciaAdapter = new OcorrenciaAdapter(this, listOcorrencia);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(ocorrenciaAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Occurrence occurrenceSelecionada = (Occurrence) ocorrenciaAdapter.getItem(position);
                goToDetail(occurrenceSelecionada);
            }
        });
    }

    public void goToDetail(Occurrence ocorrencia){
        Intent i = new Intent(this, OcorrenciaDetalheActivity.class);
        i.putExtra("ocorrencia", ocorrencia);
        startActivity(i);
    }

}
