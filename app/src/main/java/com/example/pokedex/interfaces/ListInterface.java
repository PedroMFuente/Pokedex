package com.example.pokedex.interfaces;

public interface ListInterface {
    public interface View {
        void startFormActivity();
        void startSearchActivity();
        void startAboutActivity();
    }

    public interface Presenter {
        void onClickFloatingButton();
        void onClickSearchButton();
        void onClickAboutButton();
    }
}
