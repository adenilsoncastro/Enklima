package com.example.usuario.enklima.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usuario.enklima.Model.Occurrence;
import com.example.usuario.enklima.R;

import java.util.List;

public class OcorrenciaAdapter extends BaseAdapter {

    private final Activity activity;
    private List<Occurrence> listOcorrencia;

    public OcorrenciaAdapter(Activity activity, List<Occurrence> listOcorrencia){
        this.activity = activity;
        this.listOcorrencia = listOcorrencia;
    }

    public void add(Occurrence ocorrencia){
        listOcorrencia.add(ocorrencia);
    }

    @Override
    public int getCount() {
        return listOcorrencia.size();
    }

    @Override
    public Object getItem(int position) {
        return listOcorrencia.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.list_ocorrencia, parent, false);
        Occurrence ocorrencia = listOcorrencia.get(position);

        TextView lblTitulo = (TextView) view.findViewById(R.id.lblTituloListView);
        TextView lblDescricao = (TextView) view.findViewById(R.id.lblDescricaoListView);
        ImageView imgView = (ImageView) view.findViewById(R.id.img);

        lblTitulo.setText(ocorrencia.getTitulo());
        lblDescricao.setText(ocorrencia.getDescricao());

        return view;
    }
}
