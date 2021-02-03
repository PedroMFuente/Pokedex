package com.example.pokedex.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.R;
import com.example.pokedex.models.PokemonEntity;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>
        implements View.OnClickListener{

    private ArrayList<PokemonEntity> items;
    private View.OnClickListener listener;

    // Clase interna:
    // Se implementa el ViewHolder que se encargará
    // de almacenar la vista del elemento y sus datos
    public static class PokemonViewHolder
            extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewName;
        private TextView textViewItem;

        public PokemonViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageRow);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewItem = (TextView) itemView.findViewById(R.id.textViewItem);
        }

        public void PokemonBind(PokemonEntity item) {
            byte[] decodedString = Base64.decode(item.getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
            //imageView.setText(item.getImage());
            textViewName.setText(item.getName());
            textViewItem.setText(item.getItem());
        }
    }

    // Contruye el objeto adaptador recibiendo la lista de datos
    public PokemonAdapter(@NonNull ArrayList<PokemonEntity> items) {
        this.items = items;
    }

    // Se encarga de crear los nuevos objetos ViewHolder necesarios
    // para los elementos de la colección.
    // Infla la vista del layout, crea y devuelve el objeto ViewHolder
    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_row, parent, false);
        row.setOnClickListener(this);

        PokemonViewHolder pvh = new PokemonViewHolder(row);
        return pvh;
    }

    // Se encarga de actualizar los datos de un ViewHolder ya existente.
    @Override
    public void onBindViewHolder(PokemonViewHolder viewHolder, int position) {
        PokemonEntity item = items.get(position);
        viewHolder.PokemonBind(item);
    }

    // Indica el número de elementos de la colección de datos.
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Asigna un listener al elemento
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }
}
