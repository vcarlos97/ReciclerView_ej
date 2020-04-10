package edu.upc.dsa.recyclerviewej;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateTrackActivity extends AppCompatActivity {

    private Tracks_API api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.put_layout);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://147.83.7.203:8080/swagger/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Tracks_API.class);

        final Intent track_details_intent = getIntent();
        final Intent main_intent = new Intent(UpdateTrackActivity.this, MainActivity.class);
        final String id = track_details_intent.getStringExtra("id_edit");

        Button put = (Button) findViewById(R.id.updatetrackput);
        final EditText title = (EditText) findViewById(R.id.newtracktitlefield);
        final EditText singer = (EditText) findViewById(R.id.newtracksingerfield);

        put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String titletext = title.getText().toString();
                final String singertext = singer.getText().toString();

                if (titletext.equals("") || singertext.equals("")) {
                    Toast.makeText(UpdateTrackActivity.this, "ficheros llenos", Toast.LENGTH_LONG);

                } else {
                    AlertDialog UpdatingDialogBox = new AlertDialog.Builder(UpdateTrackActivity.this)
                            .setTitle("Cargar")
                            .setMessage("Seguro que quieres cargarlo?")
                            .setPositiveButton("Lista Cargada", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Track t = new Track(id, titletext, singertext);
                                    api.updateTrack(t);
                                    dialogInterface.dismiss();
                                    startActivity(main_intent);
                                }
                            })

                            .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_LONG);
                                    dialogInterface.dismiss();

                                }
                            })

                            .create();
                    UpdatingDialogBox.create();
                    UpdatingDialogBox.show();
                }
            }
        });
    }
    public void updateTrack(Track t) {
        Call<Void> call = api.updateTrack(t);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "No se puede enviar a la API", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
