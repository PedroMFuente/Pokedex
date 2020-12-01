package com.example.pokedex.interfaces;

public interface SearchInferface {

    public interface View{
        void Searchit();
    }

    public interface Presenter{
        void onClickSearchButton();
    }
}
