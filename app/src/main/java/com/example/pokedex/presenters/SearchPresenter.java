package com.example.pokedex.presenters;

import android.util.Log;

import com.example.pokedex.interfaces.SearchInferface;

public class SearchPresenter implements SearchInferface.Presenter {

    String TAG="Pokedex/SearchPresenter";
    private SearchInferface.View view;

    public SearchPresenter(SearchInferface.View view){
        this.view=view;
    }

    @Override
    public void onClickSearchButton() {
        Log.d(TAG,"onclickSearchButton");
        view.Searchit();
    }

    @Override
    public void CallHelp() {
        view.Help();
    }
}
