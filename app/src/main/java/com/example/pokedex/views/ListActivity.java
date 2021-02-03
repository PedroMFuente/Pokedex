package com.example.pokedex.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.pokedex.R;
import com.example.pokedex.interfaces.ListInterface;
import com.example.pokedex.models.PokemonEntity;
import com.example.pokedex.presenters.ListPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements ListInterface.View {

    String TAG = "Pokedex/ListActivity";
    private ListInterface.Presenter presenter;
    private ArrayList<PokemonEntity> listspokemon= new ArrayList<PokemonEntity>();
    private RecyclerView rv;
    private PokemonAdapter adaptador;
    private TextView textmany;
    private int cc=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting Create");
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        Log.d(TAG, "Loading layout");
        setContentView(R.layout.activity_list);

        Log.d(TAG, "Loading toolbar");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new ListPresenter(this);

        if(!prefs.getBoolean("fristTime",false)){
            presenter.setPokeFirstTime();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("fristTime", true);
            editor.commit();
        }

        // Inicializa el RecyclerView
        rv = (RecyclerView) findViewById(R.id.rVlist);

        // Crea el Adaptador con los datos de la lista anterior
        adaptador = new PokemonAdapter(listspokemon);


        textmany=findViewById(R.id.textView6);

        // Asocia el Adaptador al RecyclerView
        rv.setAdapter(adaptador);

        // Muestra el RecyclerView en vertical
        rv.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper ith= new ItemTouchHelper(simpleCallback);
        ith.attachToRecyclerView(rv);

        // Asocia el elemento de la lista con una acción al ser pulsado
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                int position = rv.getChildAdapterPosition(v);
                if(listspokemon.get(position)!=null){
                    String auxid= listspokemon.get(position).getId();
                    presenter.onClickRecycledViewItem(auxid);
                }

                //Toast.makeText(ListActivity.this, "Posición: " + String.valueOf(position) + " Nombre: " + listspokemon.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        presenter = new ListPresenter(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click on Floating button");
                presenter.onClickFloatingButton();
            }
        });
    }
    PokemonEntity add=null;
    int position2=0;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position= viewHolder.getAdapterPosition();
            position2=position;
            switch (direction){
                case ItemTouchHelper.LEFT:
                    add=listspokemon.get(position);
                    if(presenter.DeletePokemon(add.getId())){
                        listspokemon.remove(position);
                        textmany.setText(listspokemon.size()+getString(R.string.amountlist));
                        adaptador.notifyItemRemoved(position);
                    }
            }
        }
    };

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("EY","Fuera");
        for(PokemonEntity p: presenter.getAll()){
            Log.d("ey",p.toString());
        }
        if(cc==-1){
            Log.d("EY","Dentro");
            listspokemon.clear();
            listspokemon.addAll(presenter.getAll());
            adaptador.notifyDataSetChanged();
            textmany.setText(listspokemon.size()+getString(R.string.amountlist));
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id== R.id.action_search){
            presenter.onClickSearchButton();
        }
        if(id == R.id.action_about){
            presenter.onClickAboutButton();
        }
        if(id == R.id.action_refresh){
            cc=-1;
            listspokemon.clear();
            listspokemon.addAll(presenter.getAll());
            adaptador.notifyDataSetChanged();
            textmany.setText(listspokemon.size()+getString(R.string.amountlist));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startFormActivity() {
        Log.d(TAG, "StartFormActivity");
        Intent intent = new Intent(getApplicationContext(), FormActivity.class);
        startActivity(intent);
    }

    @Override
    public void startSearchActivity() {
        Log.d(TAG,"StartSearchActivity");
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivityForResult(intent,0);
    }

    @Override
    public void startAboutActivity() {
        Log.d(TAG, "StartAboutActivity");
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void startFormActivity(String id) {
        Intent intent = new Intent(getApplicationContext(), FormActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    public void DeletePokemon() {
        //Snackbar.make(rv,R.string.deltext, Snackbar.LENGTH_LONG).setAction(R.string.)
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_CANCELED){
            cc=-1;
        }else{
            cc=0;
            String date=null;
            date=data.getExtras().getString("date");
            String name=null;
            name=data.getExtras().getString("name");
            String type1=null;
            type1=data.getExtras().getString("type1");
            listspokemon.clear();

            if(name!=null){
                listspokemon.addAll(presenter.searchname(name));
            }else if(date!=null){
                listspokemon.addAll(presenter.searchdate(date));
            }else if(type1!=null){
                listspokemon.addAll(presenter.searchtype(type1));
            }

            adaptador.notifyDataSetChanged();
            textmany.setText(Integer.toString(listspokemon.size())+getString(R.string.amountlist));
        }
    }
}