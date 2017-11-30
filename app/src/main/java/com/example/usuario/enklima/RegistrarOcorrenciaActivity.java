package com.example.usuario.enklima;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RemoteController;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.enklima.Model.Occurrence;
import com.example.usuario.enklima.Model.User;
import com.example.usuario.enklima.Utils.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistrarOcorrenciaActivity extends AppCompatActivity {

    private final String wsUrl = "http://35.167.241.68:5000";
    private final String wsUrlDev = "http://localhost:3000/";
    private final String wsNewOccurrenceUrl = wsUrl + "/occurrences/insert";
    private final String wsNewOccurrenceUrlDev = wsUrlDev + "/occurrences/insert";

    private User user;

    private EditText txtTitulo;
    private EditText txtOcorrencia;
    private ImageView img;
    private ProgressBar pbRegistrar;

    private String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Occurrence ocorrencia;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_ocorrencia);

        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");

        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtOcorrencia = (EditText) findViewById(R.id.txtOcorrencia);
        img = (ImageView) findViewById(R.id.img);
        pbRegistrar = (ProgressBar)findViewById(R.id.pbRegistrar);

        ocorrencia = new Occurrence();

        queue = Volley.newRequestQueue(this);
    }

    public void btnTirarFoto(View v)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            try
            {
                File photoFile = FileUtils.createFile(this, ".jpg");
                mCurrentPhotoPath = photoFile.getAbsolutePath();

                Uri photUri = FileProvider.getUriForFile(this, "com.example.usuario.enklima.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bitmap imgBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            img.setImageBitmap(imgBitmap);

            File file = new File(mCurrentPhotoPath);
            final String content;

            try
            {
                ocorrencia.setImage(FileUtils.readFromFile(file));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void btnSendOccurrenceClick(View v){

        ocorrencia.setTitulo(txtTitulo.getText().toString());
        ocorrencia.setDescricao(txtOcorrencia.getText().toString());

        if(ocorrencia.getTitulo().isEmpty()){
            Toast.makeText(RegistrarOcorrenciaActivity.this, "Título vazio!", Toast.LENGTH_LONG).show();
            return;
        }
        if(ocorrencia.getDescricao().isEmpty()){
            Toast.makeText(RegistrarOcorrenciaActivity.this, "Descrição vazia!", Toast.LENGTH_LONG).show();
            return;
        }

        final JSONObject js = new JSONObject();
        try
        {
            js.put("title", ocorrencia.getTitulo());
            js.put("details", ocorrencia.getDescricao());
            js.put("idUser", user.getId());
            if(ocorrencia.getImage() != null)
                js.put("image",  Base64.encode(ocorrencia.getImage(), 1));
        }
        catch (JSONException e){
            Toast.makeText(RegistrarOcorrenciaActivity.this, "Erro ao converter objeto para json!", Toast.LENGTH_LONG).show();
        }

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch(i){
                    case DialogInterface.BUTTON_POSITIVE:

                        pbRegistrar.setVisibility(View.VISIBLE);

                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                                Request.Method.POST,wsNewOccurrenceUrl, js,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        pbRegistrar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(RegistrarOcorrenciaActivity.this, "Ocorrência enviada com sucesso!", Toast.LENGTH_LONG).show();
                                        txtTitulo.setText("");
                                        txtOcorrencia.setText("");
                                        img.setImageBitmap(null);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        pbRegistrar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(RegistrarOcorrenciaActivity.this, "Erro ao enviar a ocorrência!" + error.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                return headers;
                            }
                        };
                        queue.add(jsonObjReq);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(RegistrarOcorrenciaActivity.this, "Envio cancelado!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarOcorrenciaActivity.this);
        builder.setMessage("Confirma o envio?").setPositiveButton("Sim", dialogClickListener)
                .setNegativeButton("Não", dialogClickListener).show();


    }
}
