package edu.upc.dsa.recyclerviewej;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {//RECETA DE COCINA QUE CASI SIEMPRE FUNCIONA, CAMBIAN MUY POCAS COSAS
    private List<Track> values;
    Activity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView id;
        public View layout;
        public Button imagen;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            title = (TextView) v.findViewById(R.id.firstLine);
            id = (TextView) v.findViewById(R.id.secondLine);
            imagen = (Button) v.findViewById(R.id.icon);
        }
    }

    public void add(int position, Track item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Track> myDataset, Activity activity) {
        values = myDataset;
        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //RECETA
        // create a new view
        LayoutInflater inflater = LayoutInflater.from (parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) { //Cuando un elemento hace binding
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Track track = values.get(position); //RECUPERA ELEMENTO QUE QUIERES VISUALIZAR
        final ViewHolder vh = holder; //HEMOS TENIDO QUE CREARLA PORQUE ESTA ARRIBA EN LA CLASE Y EN EL REMOVE NO LA PODRIAMOS UTILIZAR
        holder.title.setText(track.getTitle());
        holder.id.setText(track.getSinger());
        holder.imagen.setOnClickListener(new OnClickListener() { //ASIGNARLE EVENTO DE UN CLICK
            @Override
            public void onClick(View v) {
                Intent trackview = new Intent(activity.getApplicationContext(), TrackDetailActivity.class);
                trackview.putExtra("id",track.getId());
                trackview.putExtra("title", track.getTitle());
                trackview.putExtra("singer", track.getSinger());
                activity.startActivity(trackview);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() { //PARA QUE FUNCIONE TIENE QUE TENER CUANTOS ELEMNTOS TIENE

        return values.size();
    }

}
