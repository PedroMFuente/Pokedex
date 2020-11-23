package com.example.pokedex.interfaces;

public interface FormInterface {
    public interface View {
        void startFormActivity();
    }

    public interface Presenter {
        void onClickSaveButton();
        String getError(String name);
        //void onClickCancelButton();
    }
}
