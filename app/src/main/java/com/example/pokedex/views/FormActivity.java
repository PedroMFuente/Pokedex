package com.example.pokedex.views;

import android.content.Intent;
import android.os.Bundle;

import com.example.pokedex.R;
import com.example.pokedex.interfaces.FormInterface;
import com.example.pokedex.presenters.FormPresenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class FormActivity extends AppCompatActivity implements FormInterface.View{

    String TAG = "Pokedex/FormActivity";
    private FormInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting Create");
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Loading layout");
        setContentView(R.layout.activity_form);

        Log.d(TAG, "Loading toolbar");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Spinner 1
        Log.d(TAG, "Spinner1");
        Spinner spinner = (Spinner) findViewById(R.id.tipe1);
        String[] tipelist = {"Electrico","Fuego","Agua","Planta"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipelist));

        //Spinner 2
        Log.d(TAG, "Spinner1");
        Spinner spinner2 = (Spinner) findViewById(R.id.tipe2);
        String[] tipe2list = {"Acero","Psiquico","Siniestro","Volador"};
        spinner2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipe2list));

        //Boton para ir atrás
        Log.d(TAG,"Botón para ir hacia atrás");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Asignar la acción necesaria. En este caso "volver atrás"
                onBackPressed();
            }
        });

        presenter = new FormPresenter((FormInterface.View) this);
        Button button2 =findViewById(R.id.buttonSave);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click on SAVE button");
                presenter.onClickSaveButton();
            }

        });

        /*presenter = new FormPresenter((FormInterface.View) this);
        Button button1 =findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Click on CANCEL button");
                //presenter.onClickCancelButton();
                onBackPressed();
            }
        });*/
    }

    @Override
    public void startFormActivity() {
        Log.d(TAG, "StartFormActivity");
        Intent intent = new Intent(getApplicationContext(), FormActivity.class);
        startActivity(intent);
    }
}