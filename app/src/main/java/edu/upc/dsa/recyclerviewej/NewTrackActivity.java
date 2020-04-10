package edu.upc.dsa.recyclerviewej;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewTrackActivity extends AppCompatActivity {

    private Tracks_API api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/swagger/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Tracks_API.class);

        Button send = (Button) findViewById(R.id.newtrackpost);
        final EditText id = (EditText) findViewById(R.id.newtrackidfield);
        final EditText title = (EditText) findViewById(R.id.newtracktitlefield);
        final EditText singer = (EditText) findViewById(R.id.newtracksingerfield);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idtext = id.getText().toString();
                String titletext = title.getText().toString();
                String singertext = singer.getText().toString();

                if (titletext.equals("") || idtext.equals("") || singertext.equals("")) {
                    Toast.makeText(NewTrackActivity.this, "ficheros llenos", Toast.LENGTH_LONG);
                }
                else {
                    Track t = new Track(idtext, titletext, singertext);
                    NewTrackActivity.this.finish();
                   // Intent intent = new Intent(NewTrackActivity.this, MainActivity.class);
                    //startActivity(intent);
                }

            }
        });
    }

    public void saveTrack (Track t ){
        Call<Track> call = api.saveTrack(t);
        call.enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {

                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getApplicationContext(), "Error: "+response.code(), Toast.LENGTH_LONG);
                    toast.show();
                }

            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {

                Toast toast = Toast.makeText(getApplicationContext(), "No se puede enviar a la API", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
