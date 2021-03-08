package com.example.pokedex.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.pokedex.R;
import com.example.pokedex.interfaces.FormInterface;
import com.example.pokedex.models.PokemonEntity;
import com.example.pokedex.models.PokemonModel;
import com.example.pokedex.views.FormActivity;
import com.example.pokedex.views.MyApplication;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class FormPresenter implements FormInterface.Presenter {

    String TAG = "Pokedex/FormPresenter";
    private FormInterface.View view;
    private PokemonModel pokemodel;

    public FormPresenter(FormInterface.View view){
        this.view=view;
        pokemodel = new PokemonModel();
    }

    @Override
    public void onClickSaveButton(PokemonEntity poke) {
        Log.d(TAG, "onClickSaveButton");
        if(pokemodel.insert(poke)){
            view.SavePokemon();
            view.finishFormActivity();
        }else{
            //error
            view.NoSavePokemon();
        }

    }

    @Override
    public void onClickEditButton(PokemonEntity poke) {
        if(pokemodel.update(poke)){
            view.SavePokemon();
            view.deleteFormActivity();
        }
    }

    @Override
    public void onClickDeleteButton(String id) {
        Log.d(TAG,"onClickdeletebutton");
        if(pokemodel.delete(id)){
            view.DeleteAcp();
            view.finishFormActivity();
        }else{
            view.Deletent();
        }
    }

    @Override
    public PokemonEntity GetPokemonById(String id) {
        return pokemodel.getPokeEntity(id);
    }

    @Override
    public void PermissionRefused() {
        view.PermissionRefused();
    }

    @Override
    public void PermissionGiven() {
        view.PermissionGiven();
    }

    @Override
    public void ShowGallery() {
        view.SelectPhoto();
    }

    @Override
    public void BackToList() {
        view.finishFormActivity();
    }

    @Override
    public void WriteExternalStoragePermission() {
        int wesp = ContextCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if(wesp != PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                view.PermissionRequest();
            }else{
                PermissionRefused();
            }
        }else{
            PermissionGiven();
        }
    }

    @Override
    public List<String> GetType() {
        return pokemodel.getSpinnerType();
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

    @Override
    public void CallHelp() {
        view.Help();
    }


}
