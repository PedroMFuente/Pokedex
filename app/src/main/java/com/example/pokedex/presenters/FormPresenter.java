package com.example.pokedex.presenters;

import android.util.Log;
import android.view.View;

import com.example.pokedex.R;
import com.example.pokedex.interfaces.FormInterface;
import com.example.pokedex.views.MyApplication;
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

    public String getError(String error_code){
        String error_msg="";
        switch(error_code){
            case ("Name"):
                error_msg = MyApplication.getContext().getResources().getString(R.string.error_name);
                break;

            case(""):
                error_msg="";
        }
        return error_msg;
    }



}
