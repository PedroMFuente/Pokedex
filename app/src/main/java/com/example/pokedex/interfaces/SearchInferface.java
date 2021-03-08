package com.example.pokedex.interfaces;

public interface SearchInferface {

    public interface View{
        void Searchit();
        void Help();
    }

    public interface Presenter{
        void onClickSearchButton();
        void CallHelp();
    }
}
