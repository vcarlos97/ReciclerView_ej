package edu.upc.dsa.recyclerviewej;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrackDetailActivity extends AppCompatActivity {
    private Tracks_API api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_track_layout);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://147.83.7.203:8080/swagger/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Tracks_API.class);

        final Intent adapter_intent = getIntent();

        final String id = adapter_intent.getStringExtra("id");
        final String title = adapter_intent.getStringExtra("title");
        final String singer = adapter_intent.getStringExtra("singer");

        final Intent intent_main = new Intent(TrackDetailActivity.this, MainActivity.class);
        final Intent update_intent = new Intent(TrackDetailActivity.this, UpdateTrackActivity.class);

        final TextView id_text = (TextView) findViewById(R.id.viewtrackidfield);
        TextView title_text = (TextView) findViewById(R.id.viewtracktitelfield);
        TextView singer_text = (TextView) findViewById(R.id.viewtracksingerfield);
        id_text.setText(id);
        title_text.setText(title);
        singer_text.setText(singer);

        Button edit_button = (Button) findViewById(R.id.btneditar);
        Button delete_button = (Button) findViewById(R.id.btnborrar);
        Button info_button = (Button) findViewById(R.id.btninfo);

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(TrackDetailActivity.this)
                        .setTitle("Borrar")
                        .setMessage("Seguro que quieres borrar" +title+ "de" +singer+ "?")
                        .setPositiveButton("Delete Track", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                api.deleteTrack(id);
                                dialogInterface.dismiss();
                                startActivity(intent_main);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Operacion cancelada", Toast.LENGTH_LONG);
                                toast.show();
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                myQuittingDialogBox.create();
                myQuittingDialogBox.show();

            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_intent.putExtra("id_edit", id);
                startActivity(update_intent);
            }
        });
    }

    public void deleteTrack (String id){
        Call<Void> call = api.deleteTrack(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getApplicationContext(), "Error:" +response.code(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "No se puede borrar de la API", Toast.LENGTH_LONG);
                toast.show();

            }
        });
    }
}

