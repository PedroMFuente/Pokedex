package com.example.pokedex.presenters;

import android.util.Log;
import android.view.View;

import com.example.pokedex.interfaces.FormInterface;
import com.google.android.material.snackbar.Snackbar;

public class FormPresenter implements FormInterface.Presenter {

    String TAG = "Pokedex/FormPresenter";
    private FormInterface.View view;

    public FormPresenter(FormInterface.View view){
        this.view=view;
    }
    @Override
    public void onClickSaveButton() {
        Log.d(TAG, "onClickSaveButton");
        //mostrar en pantalla Guardado
    }
    /*
    @Override
    public void onClickCancelButton() {
        Log.d(TAG, "onClickCancelButton");
        //onBackPressed();
    }*/
}
