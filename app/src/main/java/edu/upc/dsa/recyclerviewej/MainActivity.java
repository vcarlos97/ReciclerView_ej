package edu.upc.dsa.recyclerviewej;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    private Tracks_API api;
    private List<Track> trackList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //LLAMAMOS ONCREATE PADRE
        setContentView(R.layout.activity_main);//LAYOUT QUE QUEREMOS HACER
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.my_recycler_view);// OBTENEMOS EL RECYCLERVIWE
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);//OPTIMIZAR RENDIMIENTO


        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://147.83.7.203:8080/swagger/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Tracks_API.class);

        api.getTracks();

        Button bt = findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NewTrackActivity.class);
                startActivity(i);
            }
        });
    }
    public void getTracks(){
        Call<List<Track>> call = api.getTracks();

        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG);
                    toast.show();
                }
                trackList = response.body();
                // define an adapter
                mAdapter = new MyAdapter(trackList, MainActivity.this);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),"Error inesperado", Toast.LENGTH_LONG);
                toast.show();

            }
        });
    }
}

