package com.example.pokedex.views;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import com.example.pokedex.interfaces.SearchInferface;
import com.example.pokedex.presenters.FormPresenter;
import com.example.pokedex.presenters.SearchPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pokedex.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class SearchActivity extends AppCompatActivity implements SearchInferface.View {

    String TAG = "Pokedex/SearchActivity";
    private SearchInferface.Presenter presenter;
    DatePickerDialog datepickerdialog;
    Context mycontext=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting Create");
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Loading layout");
        setContentView(R.layout.activity_search);

        Log.d(TAG, "Loading toolbar");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Spinner
        Log.d(TAG, "Spinner");
        Spinner spinner = findViewById(R.id.Searchspinner);
        ArrayList<String> tipelist = new ArrayList<String>();
        tipelist.add("--NINGUNO--");
        tipelist.add("ELECTRICO");
        tipelist.add("FUEGO");
        tipelist.add("AGUA");
        tipelist.add("PLANTA");
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipelist));

        //Boton Search
        presenter = new SearchPresenter((SearchInferface.View) this);
        Button searchbutton = findViewById(R.id.searchbutton);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Click button search");
                presenter.onClickSearchButton();
            }
        });

        //Fecha
        int year, month, day;

        Calendar cal = Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);

        TextView dateET = findViewById(R.id.SearchtextView);
        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepickerdialog = new DatePickerDialog(mycontext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateET.setText(String.valueOf(dayOfMonth)+ "/" + String.valueOf(month) +"/"+String.valueOf(year));
                    }
                }, year, month, day);
                datepickerdialog.show();
            }
        });


        //Boton para ir atrás
        Log.d(TAG,"Botón para ir hacia atrás");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Asignar la acción necesaria. En este caso "volver atrás"
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
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
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public void Searchit() {
        finish();
    }
}