package com.example.pokedex.presenters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pokedex.R;
import com.example.pokedex.interfaces.FormInterface;
import com.example.pokedex.views.FormActivity;
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
        view.finishFormActivity();

    }

    @Override
    public void onClickDeleteButton() {
        Log.d(TAG,"onClickdeletebutton");
        view.deleteFormActivity();
    }

    public String getError(String error_code){
        String error_msg="";
        switch(error_code){
            case ("Name"):
                error_msg = MyApplication.getContext().getResources().getString(R.string.error_name);
                break;
            case ("Item"):
                error_msg = MyApplication.getContext().getResources().getString(R.string.error_item);
                break;
            case("Attack"):
                error_msg= MyApplication.getContext().getResources().getString(R.string.error_attack);
                break;
            case("Hp"):
                error_msg=MyApplication.getContext().getResources().getString(R.string.error_hp);
                break;
            case("Type1"):
                error_msg=MyApplication.getContext().getResources().getString(R.string.error_type1);
                break;
            case("Type2"):
                error_msg=MyApplication.getContext().getResources().getString(R.string.error_type2);
                break;
            case("Date"):
                error_msg=MyApplication.getContext().getResources().getString(R.string.error_date);
                break;
            case(""):
                error_msg="";
                break;
        }
        return error_msg;
    }



}
