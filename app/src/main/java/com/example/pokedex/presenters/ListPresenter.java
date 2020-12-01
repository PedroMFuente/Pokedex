package com.example.pokedex.presenters;

import android.util.Log;

import com.example.pokedex.interfaces.ListInterface;

public class ListPresenter implements ListInterface.Presenter {

    String TAG = "Pokedex/ListPresenter";
    private ListInterface.View view;

    public ListPresenter (ListInterface.View view) {
        this.view = view;
    }
    @Override
    public void onClickFloatingButton() {
        Log.d(TAG, "onClickFloatinbutton");
        view.startFormActivity();
    }

    @Override
    public void onClickSearchButton(){
        Log.d(TAG,"onclickSearchButton");
        view.startSearchActivity();
    }

    @Override
    public void onClickAboutButton() {
        Log.d(TAG, "onclicAboutButton");
        view.startAboutActivity();
    }
}
