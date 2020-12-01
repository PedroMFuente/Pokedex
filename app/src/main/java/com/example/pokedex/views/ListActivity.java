package com.example.pokedex.views;

import android.app.Notification;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import com.example.pokedex.R;
import com.example.pokedex.interfaces.ListInterface;
import com.example.pokedex.presenters.ListPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;

public class ListActivity extends AppCompatActivity implements ListInterface.View {

    String TAG = "Pokedex/ListActivity";
    private ListInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting Create");
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Loading layout");
        setContentView(R.layout.activity_list);

        Log.d(TAG, "Loading toolbar");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
        startActivity(intent);
    }

    @Override
    public void startAboutActivity() {
        Log.d(TAG, "StartAboutActivity");
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }
}