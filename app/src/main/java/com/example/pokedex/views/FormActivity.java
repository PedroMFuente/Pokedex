package com.example.pokedex.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.pokedex.R;
import com.example.pokedex.interfaces.FormInterface;
import com.example.pokedex.models.PokemonEntity;
import com.example.pokedex.presenters.FormPresenter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormActivity extends AppCompatActivity implements FormInterface.View{

    String TAG = "Pokedex/FormActivity";
    private FormInterface.Presenter presenter;
    private ArrayAdapter<String> adapter;
    Context mycontext=this;
    DatePickerDialog datepickerdialog;
    private String id;
    private ImageView imgG;
    private ConstraintLayout cl;
    private PokemonEntity aux;
    private Switch shiny;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting Create");
        super.onCreate(savedInstanceState);

        presenter = new FormPresenter(this);

        Log.d(TAG, "Loading layout");
        setContentView(R.layout.activity_form);

        Log.d(TAG, "Loading toolbar");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgG=findViewById(R.id.imageGalery);
        imgG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.WriteExternalStoragePermission();
            }
        });

        cl=findViewById(R.id.consLay);
        shiny = findViewById(R.id.switch1);

        id=getIntent().getStringExtra("id");

        //Spinner 1
        Log.d(TAG, "Spinner1");
        Spinner spinner = (Spinner) findViewById(R.id.tipe1);
        ArrayList<String> tipelist = new ArrayList<String>();
        tipelist.add("--NINGUNO--");
        tipelist.add("ELECTRICO");
        tipelist.add("FUEGO");
        tipelist.add("AGUA");
        tipelist.add("PLANTA");
        tipelist.add("HIELO");
        tipelist.add("TIERRA");
        tipelist.add("PSIQUICO");
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
                    if(poke.setType1(t1ET.getSelectedItem().toString())){
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
                    if(poke.setType2(t2ET.getSelectedItem().toString())){
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
                        presenter.onClickDeleteButton(id);
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

        if(id!=null){
            aux=presenter.GetPokemonById(id);

            if(aux!=null){
                nameET.setText(aux.getName());
                itemET.setText(aux.getItem());
                attackET.setText(Integer.toString(aux.getAttack()));
                hpET.setText(Integer.toString(aux.getHp()));
                dateET.setText(aux.getDate());
                t1ET.setSelection(adapter.getPosition(aux.getType1()));
                t2ET.setSelection(adapter.getPosition(aux.getType2()));
                shiny.setChecked(aux.isShiny());

                if(poke.getImage()!=null && !aux.equals("")){
                    byte[] b = Base64.decode(aux.getImage(), Base64.DEFAULT);
                    Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
                    imgG.setImageBitmap(bm);
                }
              }

        }else{
            t1ET.setSelection(adapter.getPosition(MyApplication.getContext().getString(R.string.spinnertype)));
            t2ET.setSelection(adapter.getPosition(MyApplication.getContext().getString(R.string.spinnertype)));
            deletebut.setEnabled(false);
            deletebut.setVisibility(View.INVISIBLE);
        }

        Button butdelimg = findViewById(R.id.buttonClearimg);
        butdelimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgG.setImageBitmap(null);
                imgG.setBackground(ContextCompat.getDrawable(mycontext, R.drawable.camara__));
            }
        });

        //Boton save
        presenter = new FormPresenter((FormInterface.View) this);
        Button button2 =findViewById(R.id.buttonSave);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click on SAVE button");

                AlertDialog.Builder alertsv= new AlertDialog.Builder(FormActivity.this);
                alertsv.setMessage(MyApplication.getContext().getResources().getString(R.string.quesSavePokemon));
                alertsv.setPositiveButton(MyApplication.getContext().getResources().getString(R.string.yes), new DialogInterface.OnClickListener(){
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean result=true;
                                PokemonEntity poke = new PokemonEntity();

                                //name
                                boolean np= poke.setName(nameET.getText().toString());
                                if(np){
                                    nameL.setError(presenter.getError(""));
                                }else{
                                    result=false;
                                    nameL.setError(presenter.getError("Name"));
                                }

                                //date
                                np=poke.setDate(dateET.getText().toString());
                                if(np){
                                    dateL.setError(presenter.getError(""));
                                }else{
                                    result=false;
                                    dateL.setError(presenter.getError("Date"));
                                }

                                //attack
                                np=poke.setAttack(attackET.getText().toString());
                                if(np){
                                    attackL.setError(presenter.getError(""));
                                }else{
                                    result=false;
                                    attackL.setError(presenter.getError("Attack"));
                                }

                                //hp
                                np=poke.setHp(hpET.getText().toString());
                                if(np){
                                    hpL.setError(presenter.getError(""));
                                }else{
                                    result=false;
                                    hpL.setError(presenter.getError("Hp"));
                                }

                                //type1
                                np=poke.setType1(t1ET.getSelectedItem().toString());
                                if(np){
                                    t1L.setError(presenter.getError(""));
                                }else{
                                    result=false;
                                    t1L.setError(presenter.getError("Type1"));
                                }

                                //type2
                                np=poke.setType2(t2ET.getSelectedItem().toString());
                                if (np) {
                                    t2L.setError(presenter.getError(""));
                                }else{
                                    result=false;
                                    t2L.setError(presenter.getError("Type2"));
                                }

                                //item
                                np=poke.setItem(itemET.getText().toString());
                                if(np){
                                    itemL.setError(presenter.getError(""));
                                }else{
                                    result=false;
                                    itemL.setError(presenter.getError("Item"));
                                }

                                poke.setShiny(shiny.isChecked());

                                //image
                                if(imgG!=null&&imgG.getDrawable()!=null){
                                    Bitmap bm = ((BitmapDrawable) imgG.getDrawable()).getBitmap();
                                    if(bm!=null){
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                        byte[] bArray = baos.toByteArray();
                                        String imgIn64 = Base64.encodeToString(bArray, Base64.DEFAULT);
                                        poke.setImage(imgIn64);
                                    }
                                }

                                if(result){
                                    if(id==null){
                                        //no tiene id => es nuevo
                                        presenter.onClickSaveButton(poke);
                                    }else{
                                        //tiene id => updatear
                                        poke.setId(id);
                                        presenter.onClickEditButton(poke);
                                    }
                                }
                            }
                        });

                alertsv.setNegativeButton(MyApplication.getContext().getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });
                nameET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus){
                            boolean aux=poke.setName(nameET.getText().toString());
                            if(!aux){
                                nameL.setError(presenter.getError("Name"));
                            }else{
                                nameL.setError(presenter.getError(""));
                            }
                        }
                    }
                });

                dateET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus){
                            boolean aux=poke.setDate(dateET.getText().toString());
                            if(!aux){
                                nameL.setError(presenter.getError("Date"));
                            }else{
                                nameL.setError(presenter.getError(""));
                            }
                        }
                    }
                });

                attackET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus){
                            boolean aux=poke.setAttack(attackET.getText().toString());
                            if(!aux){
                                attackL.setError(presenter.getError("Attack"));
                            }else{
                                attackL.setError(presenter.getError(""));
                            }
                        }
                    }
                });

                hpET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus){
                            boolean aux=poke.setHp(hpET.getText().toString());
                            if(!aux){
                                hpL.setError(presenter.getError("Hp"));
                            }else{
                                hpL.setError(presenter.getError(""));
                            }
                        }
                    }
                });

                t1ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus){
                            boolean aux=poke.setType1(t1ET.getSelectedItem().toString());
                            if(!aux){
                                t1L.setError(presenter.getError("Type1"));
                            }else{
                                t1L.setError(presenter.getError(""));
                            }
                        }
                    }
                });

                t2ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus){
                            boolean aux=poke.setType2(t2ET.getSelectedItem().toString());
                            if(!aux){
                                t2L.setError(presenter.getError("Type2"));
                            }else{
                                t2L.setError(presenter.getError(""));
                            }
                        }
                    }
                });

                itemET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus){
                            boolean aux=poke.setItem(itemET.getText().toString());
                            if(!aux){
                                itemL.setError(presenter.getError("Item"));
                            }else{
                                itemL.setError(presenter.getError(""));
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

    @Override
    public void PermissionRefused() {
        Snackbar.make(cl, getResources().getString(R.string.no), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void PermissionGiven() {
        Snackbar.make(cl, getResources().getString(R.string.yes), Snackbar.LENGTH_LONG).show();
        presenter.ShowGallery();
    }

    @Override
    public void PermissionRequest() {
        ActivityCompat.requestPermissions(FormActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},123);
    }

    @Override
    public void SelectPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.choosepicture)), 201);
    }

    @Override
    public void SavePokemon() {
        Toast toast = Toast.makeText(getApplicationContext(), R.string.savebut, Toast.LENGTH_LONG);
    }

    @Override
    public void NoSavePokemon() {
        Toast toast = Toast.makeText(getApplicationContext(), R.string.nosave, Toast.LENGTH_LONG);
    }

    @Override
    public void DeleteAcp() {
        Toast toast = Toast.makeText(getApplicationContext(),getResources().getString(R.string.delbut), Toast.LENGTH_LONG);
    }

    @Override
    public void Deletent() {
        Toast toast = Toast.makeText(getApplicationContext(),getResources().getString(R.string.errordel), Toast.LENGTH_LONG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==201){
            if(resultCode== Activity.RESULT_OK){
                Uri selImg = data.getData();
                String selPath= selImg.getPath();

                if(selPath != null){
                    InputStream ims = null;
                    try{
                        ims = getContentResolver().openInputStream(selImg);
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    Bitmap bm = BitmapFactory.decodeStream(ims);

                    ImageView imv = findViewById(R.id.imageGalery);
                    imv.setImageBitmap(bm);
                    imv.setBackground(null);
                }
            }
        }
    }
}