package edu.upc.dsa.recyclerviewej;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

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


        recyclerView = findViewById(R.id.my_recycler_view);// OBTENEMOS EL RECYCLERVIWE
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);//OPTIMIZAR RENDIMIENTO


        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<String> input = new ArrayList<>();//AQUI SE HACE LA LLAMADA AL RETROFIT
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }

        // define an adapter
        mAdapter = new MyAdapter(input);
        recyclerView.setAdapter(mAdapter);
    }
}
