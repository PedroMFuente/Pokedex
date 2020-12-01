package com.example.pokedex.views;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.pokedex.R;
import com.example.pokedex.interfaces.FormInterface;
import com.example.pokedex.models.PokemonEntity;
import com.example.pokedex.presenters.FormPresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormActivity extends AppCompatActivity implements FormInterface.View{

    String TAG = "Pokedex/FormActivity";
    private FormInterface.Presenter presenter;
    private ArrayAdapter<String> adapter;
    Context mycontext=this;
    DatePickerDialog datepickerdialog;

    @SuppressLint("ResourceType")
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
        ArrayList<String> tipelist = new ArrayList<String>();
        tipelist.add("--NINGUNO--");
        tipelist.add("ELECTRICO");
        tipelist.add("FUEGO");
        tipelist.add("AGUA");
        tipelist.add("PLANTA");
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipelist));

        //Spinner 2
        Log.d(TAG, "Spinner1");
        Spinner spinner2 = (Spinner) findViewById(R.id.tipe2);
        spinner2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipelist));



        // Definición del Adaptador que contiene la lista de opciones
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tipelist);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);


        //Botón para añadir otro tipo al spinner
        Button addtype =(Button) findViewById(R.id.addtype);
        addtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutaddtype = LayoutInflater.from(mycontext);
                View viewAlertDialog = layoutaddtype.inflate(R.layout.spinnerdialog, null);

                // Definición del AlertDialog
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mycontext);

                // Asignación del AlertDialog a su vista
                alertDialog.setView(viewAlertDialog);

                // Recuperación del EditText del AlertDialog
                final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.spinnertext);


                // Configuración del AlertDialog
                alertDialog
                        .setCancelable(false)
                        // Botón Añadir
                        .setPositiveButton(getResources().getString(R.string.add),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        boolean flagtype=false;
                                        if(dialogInput.getText().toString().length()>0){
                                            Pattern pat = Pattern.compile("[A-Za-zÑñ ]+");
                                            Matcher mat = pat.matcher(dialogInput.getText().toString());
                                            if(mat.matches()){
                                                for(String e:tipelist){
                                                    if(dialogInput.getText().toString().toUpperCase().equals(e)){
                                                        flagtype=true;
                                                        spinner.setSelection(adapter.getPosition(dialogInput.getText().toString().toUpperCase()));
                                                    }
                                                }
                                                if(!flagtype){
                                                    adapter.add(dialogInput.getText().toString().toUpperCase());
                                                    spinner.setSelection(adapter.getPosition(dialogInput.getText().toString().toUpperCase()));
                                                }else{
                                                    Toast.makeText(getApplicationContext(), MyApplication.getContext().getResources().getString(R.string.exist_type),Toast.LENGTH_LONG).show();
                                                }
                                            }else{
                                                Toast.makeText(getApplicationContext(), MyApplication.getContext().getResources().getString(R.string.nvtype),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                })
                        // Botón Cancelar
                        .setNegativeButton(getResources().getString(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                })
                        .create()
                        .show();
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

        //Boton save
        presenter = new FormPresenter((FormInterface.View) this);
        Button button2 =findViewById(R.id.buttonSave);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click on SAVE button");
                presenter.onClickSaveButton();
            }

        });


        //crear un pokemon
        PokemonEntity poke = new PokemonEntity();

        //name
        TextInputEditText nameET = findViewById(R.id.textInputName);
        TextInputLayout nameL = findViewById(R.id.textname);

        nameET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.d(TAG,"Exit editText name");
                    if(poke.setName(nameET.getText().toString())){
                        nameL.setError(presenter.getError(""));
                    }else{
                        nameL.setError(presenter.getError("Name"));
                    }
                }else{
                    Log.d(TAG, "Input EditText name");
                }
            }
        });

        //item
        TextInputEditText itemET = findViewById(R.id.textInputItem);
        TextInputLayout itemL = findViewById(R.id.textItem);

        itemET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.d(TAG, "Exit editText item");
                    if(poke.setItem(itemET.getText().toString())){
                        itemL.setError(presenter.getError(""));
                    }else{
                        itemL.setError(presenter.getError("Item"));
                    }
                }else{

                }
            }
        });

        //Attack
        TextInputEditText attackET = findViewById(R.id.textInputAtt);
        TextInputLayout attackL = findViewById(R.id.textAtt);

        attackET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.d(TAG, "Exit editText attack");
                    if(poke.setAttack(attackET.getText().toString())){
                        attackL.setError(presenter.getError(""));
                    }else{
                        attackL.setError(presenter.getError("Attack"));
                    }
                }
            }
        });

        //Hp
        TextInputEditText hpET = findViewById(R.id.textInputHp);
        TextInputLayout hpL = findViewById(R.id.textHp);

        hpET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.d(TAG, "Exit editText hp");
                    if(poke.setHp(hpET.getText().toString())){
                        hpL.setError(presenter.getError(""));
                    }else{
                        hpL.setError(presenter.getError("Hp"));
                    }
                }
            }
        });

        //Type1
        Spinner t1ET = findViewById(R.id.tipe1);
        t1ET.setFocusableInTouchMode(true);
        TextInputLayout t1L =findViewById(R.id.type1Layout);

        t1ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.d(TAG,"Exit spinner type1");
                    if(poke.setType1(t1ET.getSelectedItem().toString(),tipelist)){
                        t1L.setError(presenter.getError(""));
                    }else{
                        t1L.setError(presenter.getError("Type1"));
                    }
                }
            }
        });

        //Type2
        Spinner t2ET = findViewById(R.id.tipe2);
        t2ET.setFocusableInTouchMode(true);
        TextInputLayout t2L = findViewById(R.id.type2Layout);

        t2ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.d(TAG, "Exit spinner type2");
                    if(poke.setType2(t2ET.getSelectedItem().toString(),tipelist)){
                        t2L.setError(presenter.getError(""));
                    }else{
                        t2L.setError(presenter.getError("Type2"));
                    }
                }
            }
        });

        //Eliminar
        Button deletebut = findViewById(R.id.buttonCancel);
        deletebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdelete = new AlertDialog.Builder(FormActivity.this);
                alertdelete.setTitle(MyApplication.getContext().getResources().getString(R.string.button_delete));
                alertdelete.setMessage(MyApplication.getContext().getResources().getString(R.string.deltext));

                alertdelete.setPositiveButton(MyApplication.getContext().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG,"Yes button clicked");
                        presenter.onClickDeleteButton();
                    }
                });

                alertdelete.setNegativeButton(MyApplication.getContext().getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "No button clicked");
                        dialog.dismiss();
                    }
                });
                AlertDialog aldelete = alertdelete.create();
                aldelete.show();
            }
        });


        //Calendario
        int year, month, day;

        Calendar cal = Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);

        TextInputEditText dateET = findViewById(R.id.textInputDate);
        TextInputLayout dateL = findViewById(R.id.textdate);
        Button dateBut =findViewById(R.id.butcalendar);
        dateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    datepickerdialog = new DatePickerDialog(mycontext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            //the month have "+1" because in the activity put the month before to the selected
                            dateET.setText(String.valueOf(dayOfMonth)+ "/" + String.valueOf(month+1) +"/"+String.valueOf(year));
                        }
                    }, year, month, day);
                    datepickerdialog.show();
            }
        });

        dateET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(poke.setDate(dateET.getText().toString())){
                        dateL.setError(presenter.getError(""));
                    }else{
                        dateL.setError(presenter.getError("Date"));
                    }

                }
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
    public void finishFormActivity() {
        //Save
        finish();
        Toast.makeText(getApplicationContext(), MyApplication.getContext().getResources().getString(R.string.savebut),Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public void deleteFormActivity() {
        finish();
    }
}