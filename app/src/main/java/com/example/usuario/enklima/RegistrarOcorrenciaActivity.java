package com.example.usuario.enklima;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RemoteController;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.usuario.enklima.Model.Occurrence;
import com.example.usuario.enklima.Model.User;
import com.example.usuario.enklima.Utils.FileUtils;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistrarOcorrenciaActivity extends AppCompatActivity {

    private final String wsUrl = "http://35.167.241.68:5000";
    private final String wsNewOccurrenceUrl = wsUrl + "/occurrences/insert";

    private EditText txtTitulo;
    private EditText txtOcorrencia;
    private ImageView img;

    private String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Occurrence ocorrencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_ocorrencia);

        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtOcorrencia = (EditText) findViewById(R.id.txtOcorrencia);
        img = (ImageView) findViewById(R.id.img);

        ocorrencia = new Occurrence();
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

    public void btnEnviarOcorrencia(View v){
        StringRequest postRequest = new StringRequest(Request.Method.POST, wsNewOccurrenceUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Occurrence occurrenceFromWs = new Occurrence();
                        try {
                            occurrenceFromWs.JSonToOccurrence(response);

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                params.put("title", ocorrencia.getTitulo());
                params.put("details", ocorrencia.getDescricao());
                params.put("image", ocorrencia.getImage().toString());

                return params;
            }
        };
    }
}
