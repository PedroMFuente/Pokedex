package com.example.pokedex.views;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.pokedex.interfaces.SearchInferface;
import com.example.pokedex.presenters.FormPresenter;
import com.example.pokedex.presenters.SearchPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private TextInputEditText nameEt;
    private Spinner s;
    private TextView dateET;

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

        //Fecha
        int year, month, day;

        Calendar cal = Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);

        dateET = findViewById(R.id.SearchtextView);
        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepickerdialog = new DatePickerDialog(mycontext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String zeroday=null;
                        String zeromonth=null;
                        month++;
                        if(dayOfMonth>9){
                            zeroday=Integer.toString(dayOfMonth);
                        }else{
                            zeroday="0"+dayOfMonth;
                        }
                        if(month>9){
                            zeromonth=Integer.toString(month);
                        }else{
                            zeromonth="0"+month;
                        }
                        dateET.setText(zeroday+ "/" + zeromonth +"/"+String.valueOf(year));
                    }
                }, year, month, day);
                datepickerdialog.show();
            }
        });

        s = findViewById(R.id.Searchspinner);
        nameEt = findViewById(R.id.SearchInputEditName);


        //Boton Search
        presenter = new SearchPresenter((SearchInferface.View) this);
        Button searchbutton = findViewById(R.id.searchbutton);

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                boolean result=false;

                if(nameEt!=null&&nameEt.getText().toString().length()>0){
                    i.putExtra("name",nameEt.getText().toString().toUpperCase());
                    Log.d("PASO","PUEDE");
                    setResult(RESULT_OK, i);
                    result=true;
                }else if(dateET!=null&&!dateET.getText().toString().equals("dd/mm/yyyy") ){
                    i.putExtra("date",dateET.getText().toString().toUpperCase());
                    setResult(RESULT_OK, i);
                    result=true;
                }else if(s!=null&&!s.getSelectedItem().toString().equals("--NINGUNO--")){
                    i.putExtra("type1",s.getSelectedItem().toString());
                    setResult(RESULT_OK, i);
                    result=true;
                }

                if(result){
                    finish();
                    Log.d("FUERA", String.valueOf(result));
                }
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_help){
            this.presenter.CallHelp();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void Searchit() {
        finish();
    }

    @Override
    public void Help() {
        Intent i = new Intent(getApplicationContext(), HelpActivity.class);
        i.putExtra("aux","search");
        startActivity(i);
    }
}